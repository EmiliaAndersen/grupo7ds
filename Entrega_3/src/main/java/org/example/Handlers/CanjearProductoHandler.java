package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Rol.Colaborador;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CanjearProductoHandler implements Handler {
  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    String idParam = ctx.pathParam("id");
    long productoId;
    try {
      productoId = Long.parseLong(idParam); // Convertir a long
    } catch (NumberFormatException e) {
      ctx.status(400).result("ID de producto inválido.");
      return;
    }

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    TypedQuery<OfrecerProductos> query = em.createQuery("select o from OfrecerProductos o where o.id = :id", OfrecerProductos.class);
    query.setParameter("id",productoId);

    OfrecerProductos ofrecerProductos = query.getSingleResult();

    TypedQuery<Colaborador> queryColab = em.createQuery(
        "select c from Colaborador c " +
            "JOIN c.persona p " +
            "JOIN p.usuario u " +
            "where u.usuario = :usu",
        Colaborador.class);
    String username = ctx.sessionAttribute("username");
    queryColab.setParameter("usu",username);

    Colaborador colab = queryColab.getSingleResult();

    if (colab.getPuntos() > ofrecerProductos.monto){
      colab.setPuntos(colab.getPuntos() - ofrecerProductos.monto);
      BDUtils.commit(em);
      ctx.status(200).result("Producto canjeado exitosamente.");
    }else{
      ctx.status(500).result("Error al canjear el producto.");
      em.close();
    }

  }
}
