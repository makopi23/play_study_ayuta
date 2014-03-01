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
	

	
	// /itemにアクセスした際のAction
	public static Result setitem(){
	    Form<Message> f = new Form(Message.class);
	    return ok(item.render("ID番号を入力。", f));
	}
	
	// /editにアクセスした際のAction
	public static Result edit(){
	    Form<Message> f = new Form(Message.class).bindFromRequest();
	    if(!f.hasErrors()){
	        Message obj = f.get();
	        
	        Long id = obj.id;
	        obj = Message.find.byId(id);
	        if(obj != null){
	            f = new Form(Message.class).fill(obj);
	            return ok(edit.render("ID=" + id + "の投稿を編集。", f));
	        }else{
	            return ok(item.render("ERROR:IDの投稿が見つかりません。", f));
	        }
	    }else{
	        return ok(item.render("ERROR:入力に問題があります。", f));
	    }
	}
	
	// /updateにアクセスした際のAction
	public static Result update(){
	    Form<Message> f = new Form(Message.class).bindFromRequest();
	    if(!f.hasErrors()){
	        Message data = f.get();
	        data.update();
	        return redirect("/");
	    }else{
	        return ok(edit.render("ERROR:再入力してください。", f));
	    }
	}
	
	
	// /delにアクセスした際のAction
    public static Result delete(){
        Form<Message> f = new Form(Message.class);
        return ok(delete.render("削除するID番号", f));
    }
    
    // /removeにアクセスした際のAction
    public static Result remove(){
        Form<Message> f = new Form(Message.class).bindFromRequest();
        if(!f.hasErrors()){
            Message obj = f.get();
            Long id = obj.id;
            obj = Message.find.byId(id);
            if(obj != null){
                obj.delete();
                return redirect("/");
            }else{
                return ok(delete.render("ERROR:そのID番号は見つかりません。", f));
            }
        }else{
            return ok(delete.render("ERROR:入力にエラーが起こりました。", f));
        }
    }
    
    // /findにアクセスした際のAction
    public static Result find(){
        Form<FindForm> f = new Form(FindForm.class).bindFromRequest();
        List<Message> datas = null;
        if(!f.hasErrors()){
            datas = Message.find.where().findList();
        }
        return ok(find.render("投稿の検索", f, datas));
        
    }
}
