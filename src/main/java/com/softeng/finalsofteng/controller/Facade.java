package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.ILogin;
import com.softeng.finalsofteng.model.SystemRemoteException;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.model.decorator.IBus;
import com.softeng.finalsofteng.model.decorator.BusConductor;
import com.softeng.finalsofteng.model.decorator.BusRuta;
import com.softeng.finalsofteng.model.decorator.Bus;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Facade implements ILogin {
    private static Facade instance = null;
    private final Map<String, User> users; // Map consisting of ip and user
    //private Map<BigInteger, String> ipNonce;
    private Encryption encryption;
    private final ArrayList<IBus> buses;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IZonaRespository zonaRespository;

    private Facade() {
        this.users = new HashMap<>();
        //this.ipNonce = new HashMap<>();
        this.buses = new ArrayList<>();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public void registrarSesion(String ip, User user) {
        this.users.put(ip, user);
    }

    public Object elaborarOperacion(String methodName, String ip) throws Exception {
        if (!this.users.containsKey(ip)) throw new SystemRemoteException("User not authorized");
        Proxy proxy = Proxy.getInstance();
        this.encryption = Encryption.getInstance(proxy.getNonceByIP(ip).toString());
        Method method;
        String[] parameters = encryption.decrypt(methodName).split(",");
        String nombre = parameters[0];
        switch (nombre) {
            case "crearBus":
                method = getClass().getDeclaredMethod("crearBus", String.class, String.class, String.class, int.class, String.class);
                return method.invoke(this, parameters[1], parameters[2], parameters[3], Integer.parseInt(parameters[4]), parameters[5]);
            case "verRutas":
                method = getClass().getDeclaredMethod("verRutas");
                return method.invoke(this);
            case "buscarPersonaZona":
                method = getClass().getDeclaredMethod("buscarPersonaZona", String.class);
                return method.invoke(this, parameters[1]);
            case "crearContenedor":
                method = getClass().getDeclaredMethod("crearContenedor", parameters[2].getClass(), parameters[4].getClass());
                return method.invoke(this, parameters[3], parameters[5]);
            case "crearUsuario":
                method = getClass().getDeclaredMethod("crearUsuario", parameters[2].getClass(), parameters[4].getClass(), parameters[6].getClass(), parameters[8].getClass(), parameters[10].getClass());
                return method.invoke(this, parameters[3], parameters[5], parameters[7], parameters[9], parameters[11]);
            /*case "crearUsuarioComposite":
                method = getClass().getDeclaredMethod("crearUsuarioComposite", parameters[2].getClass());
                return method.invoke(this, parameters[3]);*/
            case "adicionarUsuarioaComposite":
                method = getClass().getDeclaredMethod("adicionarUsuarioaComposite", parameters[2].getClass(), parameters[4].getClass());
                return method.invoke(this, parameters[3], parameters[5]);
            case "listarUsuarios":
                method = getClass().getDeclaredMethod("listarUsuarios", String.class);
                return method.invoke(this, parameters[3]);
        }
        return null;
    }

    private void crearBus(String conductor, String ruta, String placa, int capacidad, String marca) {
        IBus bus = new BusConductor(new BusRuta(new Bus(placa, capacidad, marca), ruta), conductor);
        this.buses.add(bus);
    }

    private String verRutas() {
        StringBuilder res = new StringBuilder();
        for (IBus bus : buses) {
            res.append(bus.getTodo()).append("\n");
        }
        return res.toString();
    }

    private String buscarPersonaZona(String nombreLugar) {
        Zona zona = zonaRespository.findByNombreLugar(nombreLugar);
        List<User> usersInZone = userRepository.findAllByZona(zona);
        return usersInZone.toString();
    }// Lo mismo que listarUsuarios()


    public void crearContenedor(String nombreLugar, Zona zonaPadre){
        Zona    zona = new Zona(nombreLugar);
        zona.setZonaPadre(zonaPadre);
        zonaRespository.save(zona);
        if (zonaPadre != null) {
            zonaPadre.add(zona);
            zonaRespository.save(zonaPadre);
        }
    }

    private String crearUsuario(String email, String password, String direccion, String documento, String telefono) {
        String retorna = "Fallo";
        User usuario = null;
        Map<String, User> usuarios = Proxy.getInstance().getUsers();
        for (User user : usuarios.values()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                usuario = user;
            }
        }
        if (usuario != null) {
            // Ver en donde se mete el usuario creado...
            Proxy proxy = Proxy.getInstance();
            User user = new User(usuario.getEmail(), usuario.getPassword(), direccion, documento, telefono);
            proxy.remplazarUsuario(usuario, user);
            retorna = "void";
        }

        return retorna;
    }

    /*
    private String crearUsuarioComposite(String documento) {
        Zona zona = new Zona();
        String retorno = "Fallo";
        User usuario = userRepository.findByDocumento(documento);
        if (usuario != null) retorno = "void";
        usuario.setZona(zona); // Esto el GarbageCollection lo borra... No hace nada
        return retorno;
    }
    */

    private String adicionarUsuarioaComposite(String nombreLugar, String documento){
        String retorno = "fallo";
        User user = userRepository.findByDocumento(documento);
        if (user == null){
            retorno = "void";
        }
        Zona zona = zonaRespository.findByNombreLugar(nombreLugar);
        user.setZona(zona);
        userRepository.save(user);
        return retorno;
    }

    private String listarUsuarios(String nombreLugar){
        Zona zona = zonaRespository.findByNombreLugar(nombreLugar);
        List<User> usersInZone = userRepository.findAllByZona(zona);
        return usersInZone.toString();
    }

    @Override
    public void registerUser(String email, String password, String direccion, String documento, String telefono, Zona zona) {
        userRepository.save(new User(email, password, direccion, documento, telefono, zona));
    }

    public String getZonasString() {

        List<Zona> zonas = zonaRespository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for (Zona zona : zonas) {
            stringBuilder.append(zona.getNombreLugar() + "\n");
        }
        return stringBuilder.toString();
    }

    public Zona existeZona(String nombreLugar) {
        return zonaRespository.findByNombreLugar(nombreLugar);

    }
}
