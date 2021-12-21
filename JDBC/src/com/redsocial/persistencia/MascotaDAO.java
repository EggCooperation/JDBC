package com.redsocial.persistencia;

import com.redsocial.dominio.mascota.Mascota;
import com.redsocial.dominio.usuario.Usuario;
import com.redsocial.dominio.usuario.UsuarioService;
import java.util.ArrayList;
import java.util.Collection;

public final class MascotaDAO extends DAO {

    private final UsuarioService usuarioService;

    public MascotaDAO() {
        this.usuarioService = new UsuarioService();
    }

    public void guardarMascota(Mascota mascota) throws Exception {
        try {
            if (mascota == null) {
                throw new Exception("Debe indicar el mascota");
            }
            String sql = "INSERT INTO Mascota (apodo, raza, idUsuario) "
                    + "VALUES ( '" + mascota.getApodo() + "' , '" + mascota.getRaza() + "' ," + mascota.getUsuario().getId() + " );";

            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void modificarMascota(Mascota mascota) throws Exception {
        try {
            if (mascota == null) {
                throw new Exception("Debe indicar el mascota que desea modificar");
            }
            String sql = "UPDATE Mascota SET "
                    + " apodo = '" + mascota.getApodo() + "' , raza = '" + mascota.getRaza() + "' , idUsuario = " + mascota.getUsuario().getId()
                    + " WHERE id = '" + mascota.getId() + "'";
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void eliminarMascota(int id) throws Exception {
        try {
            String sql = "DELETE FROM Mascota WHERE id = " + id + "";
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public Mascota buscarMascotaPorId(int id) throws Exception {
        try {
            String sql = "SELECT * FROM Mascota WHERE id = " + id + "";
            consultarBase(sql);
            Mascota mascota = null;
            while (resultado.next()) {
                mascota = new Mascota();
                mascota.setId(resultado.getInt(1));
                mascota.setApodo(resultado.getString(2));
                mascota.setRaza(resultado.getString(3));
                Integer idUsuario = resultado.getInt(4);
                Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
                mascota.setUsuario(usuario);
            }
            desconectarBase();
            return mascota;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }

    public Collection<Mascota> listarMascotas() throws Exception {
        try {
            String sql = "SELECT * FROM Mascota ";
            consultarBase(sql);
            Mascota mascota = null;
            Collection<Mascota> mascotas = new ArrayList();
            while (resultado.next()) {
                mascota = new Mascota();
                mascota.setId(resultado.getInt(1));
                mascota.setApodo(resultado.getString(2));
                mascota.setRaza(resultado.getString(3));
                Integer idUsuario = resultado.getInt(4);
                Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
                mascota.setUsuario(usuario);
                mascotas.add(mascota);
            }
            desconectarBase();
            return mascotas;
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw e;
        }
    }
}
