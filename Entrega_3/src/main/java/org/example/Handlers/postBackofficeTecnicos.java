package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class postBackofficeTecnicos implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();

    String nombre = context.formParam("nombre");
    String tipoDocumento = context.formParam("tipo_documento");
    String numeroDocumento = context.formParam("numero_documento");
    String cuil = context.formParam("tipo_medio_contacto");
    String contacto = context.formParam("contacto");
    String latitud = context.formParam("latitud");
    String longitud = context.formParam("lognitud");
    String radio = context.formParam("radio");

    try{
      model.put("successMessage","Tecnico creado correctamente");
    }catch (Exception e){
      model.put("errorMessage","Error al crear Tecnico");
    }
    context.render("/templates/backofficeTecnicos.mustache",model);
  }
}
