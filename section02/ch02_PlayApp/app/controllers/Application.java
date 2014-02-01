package controllers;

import java.util.List;

import models.Message;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
	
	public static class FindForm {
		@Required
		public String input;
	}
	    
	// ルートにアクセスした際のAction
	public static Result index() {
		List<Message> datas = Message.find.all();
		return ok(index.render("データベースのサンプル",datas));
	}

	// 新規作成フォームのAction
	public static Result add(){
		Form<Message> f = new Form(Message.class);
		return ok(add.render("投稿フォーム",f));
	}

	// /createにアクセスした際のAction
	public static Result create(){
		Form<Message> f = new Form(Message.class).bindFromRequest();
		if (!f.hasErrors()){
			Message data = f.get();
			data.save();
			return redirect("/");
		} else {
			return badRequest(add.render("ERROR", f));
		}                        
	}      
}
