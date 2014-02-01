package controllers;

import java.util.List;

import models.Message;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
    	List<Message> datas = Message.find.all();
        return ok(index.render("データベースのサンプル",datas));
    }

}
