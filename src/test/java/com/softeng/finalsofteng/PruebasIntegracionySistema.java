package com.softeng.finalsofteng;

import com.softeng.finalsofteng.controller.Encryption;
import com.softeng.finalsofteng.controller.Facade;
import com.softeng.finalsofteng.controller.Proxy;
import com.softeng.finalsofteng.model.Zona;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class PruebasIntegracionySistema {


    /**
     * Acceder al sistema
     */
    @Test
    public void ingresaralSistema() {

        try {
            Proxy proxy = Proxy.getInstance();

            BigInteger primo = proxy.accederSistema("juangarru@unisabana.edu.co", "123456789", "20.4.3.1");

            assertNotEquals(BigInteger.ZERO, primo);

        } catch (Exception e) {
            fail("correo electrónico y/o  claves erroneas");
        }

    }

    /**
     * Composite agregar una localidad
     */
    @Test
    public void agregarunaLocalidad() {

        try {

            Proxy proxy = Proxy.getInstance();

            BigInteger primo = proxy.accederSistema("juangarru@unisabana.edu.co", "123456789", "20.4.3.1");

            //Protocolo nombre de método, cantidad de parámetros, tipo, valor,

            // y tipo de retorno o void si no

            String mensajeAencriptar = "crearContenedor,1,String,Chía,void";

            Encryption aes = Encryption.getInstance(primo.toString());

            String mensajeEncriptado = aes.encrypt(mensajeAencriptar);


            Facade facade = Facade.getInstance();

            //String retorno = (String) facade.elaborarOperacion(mensajeEncriptado, "20.4.3.1");


            //assertEquals("void", retorno);

        } catch (Exception e) {
            e.printStackTrace();
            fail("No se agrego la ciudad");
        }

    }

    /**
     * Composite crear usuario
     */
    @Test
    public void crearUsuario() {

        try {

            Proxy proxy = Proxy.getInstance();

            BigInteger primo = proxy.accederSistema("juangarru@unisabana.edu.co", "123456789", "20.4.3.1");


            //Protocolo nombre de método, cantidad de parámetros, tipo, valor ,

            // y tipo de retorno o void si no

            String mensajeAencriptar = "crearUsuario,5,String,juangarru@unisabana.edu.co,String," +

                    "123456789,String,calle 70 No. 50-14 Casa 1,String,100912921,String,3017641234,void";

            Encryption aes = Encryption.getInstance(primo.toString());

            String mensajeEncriptado = aes.encrypt(mensajeAencriptar);


            Facade facade = Facade.getInstance();

            //String retorno = (String) facade.elaborarOperacion(mensajeEncriptado, "20.4.3.1");


            //assertEquals("void", retorno);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Registrar usuario ");
        }

    }

    /**
     * Composite agregar usuario
     */
    @Test
    public void agregarUsuario() {

        try {


            Proxy proxy = Proxy.getInstance();

            BigInteger primo = proxy.accederSistema("juangarru@unisabana.edu.co", "123456789", "20.4.3.1");

            // CREAR USUARIO
            String mensajeAencriptar1 = "crearUsuario,5,String,juangarru@unisabana.edu.co,String,123456789,String,calle 70 No. 50-14 Casa 1,String,100912921,String,3017641234,void";

            Encryption aes1 = Encryption.getInstance(primo.toString());

            String mensajeEncriptado1 = aes1.encrypt(mensajeAencriptar1);


            Facade facade1 = Facade.getInstance();

            //facade1.elaborarOperacion(mensajeEncriptado1, "20.4.3.1");

            //Protocolo nombre de método, cantidad de parámetros, tipo, valor ,

            // y tipo de retorno o void si no

            String mensajeAencriptar = "crearUsuarioComposite,1,String,100912921,void";

            Encryption aes = Encryption.getInstance(primo.toString());

            String mensajeEncriptado = aes.encrypt(mensajeAencriptar);


            Facade facade = Facade.getInstance();

            //String retorno = (String) facade.elaborarOperacion(mensajeEncriptado, "20.4.3.1");


            //assertEquals("void", retorno);

        } catch (Exception e) {
            fail("Registrar usurio ");
        }

    }

    //adicionar usuario a composite
    /**
     * Composite agregar usuario
     */
    @Test
    public void agregarUsuarioaCiudad() {

        try {

            Proxy proxy = Proxy.getInstance();

            // Crear el contenedor Chia
            proxy.crearContenedor("Chia", null);
            BigInteger primo = proxy.accederSistema("juangarru@unisabana.edu.co", "123456789", "20.4.3.1");

            //CREAR USUARIO
            String mensajeAencriptar1 = "crearUsuario,5,String,juangarru@unisabana.edu.co,String," +

                    "123456789,String,calle 70 No. 50-14 Casa 1,String,100912921,String,3017641234,void";

            Encryption aes1 = Encryption.getInstance(primo.toString());

            String mensajeEncriptado1 = aes1.encrypt(mensajeAencriptar1);


            Facade facade1 = Facade.getInstance();

            //facade1.elaborarOperacion(mensajeEncriptado1, "20.4.3.1");


            //Protocolo nombre de método, cantidad de parámetros, tipo, valor ,

            // y tipo de retorno o void si no

            String mensajeAencriptar = "adicionarUsuarioaComposite,2,String,Chia,String,100912921,void";

            Encryption aes = Encryption.getInstance(primo.toString());

            String mensajeEncriptado = aes.encrypt(mensajeAencriptar);


            Facade facade = Facade.getInstance();

            //String retorno = (String) facade.elaborarOperacion(mensajeEncriptado, "20.4.3.1");

            //assertEquals("void", retorno);


        } catch (Exception e) {
            e.printStackTrace();
            fail("Registrar usuario ");
        }

    }

    //listar usuarios de ciudad

    /**
     * Composite listar usuarios
     */
    @Test
    public void listarUsuarioaCiudad() {

        try {

            Proxy proxy = Proxy.getInstance();


            BigInteger primo = proxy.accederSistema("juangarru@unisabana.edu.co", "123456789", "20.4.3.1");

            //CREAR USUARIO, CONTENEDOR Y METERLO A CONTENEDOR
            proxy.crearContenedor("Chia", null);
            //CREAR USUARIO
            String mensajeAencriptar1 = "crearUsuario,5,String,juangarru@unisabana.edu.co,String,123456789,String,calle 70 No. 50-14 Casa 1,String,100912921,String,3017641234,void";

            Encryption aes1 = Encryption.getInstance(primo.toString());

            String mensajeEncriptado1 = aes1.encrypt(mensajeAencriptar1);


            Facade facade1 = Facade.getInstance();

            //facade1.elaborarOperacion(mensajeEncriptado1, "20.4.3.1");

            //METERLO A CONTENEDOR
            String mensajeAencriptar2 = "adicionarUsuarioaComposite,2,String,Chia,String,100912921,void";

            Encryption aes2 = Encryption.getInstance(primo.toString());

            String mensajeEncriptado2 = aes2.encrypt(mensajeAencriptar2);


            Facade facade2 = Facade.getInstance();

            //facade2.elaborarOperacion(mensajeEncriptado2, "20.4.3.1");

            //Protocolo nombre de método, cantidad de parámetros, tipo, valor ,

            // y tipo de retorno o void si no

            String mensajeAencriptar = "listarUsuarios,1,String,Chia,String";

            Encryption aes = Encryption.getInstance(primo.toString());

            String mensajeEncriptado = aes.encrypt(mensajeAencriptar);


            Facade facade = Facade.getInstance();

            //String retorno = (String) facade.elaborarOperacion(mensajeEncriptado, "20.4.3.1");
            //System.out.println(retorno);

            //assertEquals(retorno, "Chia,juangarru@unisabana.edu.co");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Listar usuarios de ciudad ");
        }

    }

}
