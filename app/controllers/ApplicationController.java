package controllers;

import play.mvc.*;
import views.html.*;

public class ApplicationController extends Controller {

   public Result saludo(String nombre) {
      return ok(saludo.render(nombre));
   }

    public Result AcercaDe() {
        return ok(acercaDe.render());
    }
}