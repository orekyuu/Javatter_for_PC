package javatter.plugin.omikuzi;

import com.orekyuu.javatter.controller.UserStreamController;
import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.plugin.JavatterPlugin;
import com.orekyuu.javatter.view.IJavatterTab;

public class OmikuziPlugin extends JavatterPlugin{

	@Override
	public void init() {
		//コントローラを登録
		UserStreamController controller = new UserStreamController();
		UserStreamLogic omikuziLogic = new OmikuziModel(getSaveData());
		controller.setModel(omikuziLogic);
		addUserStreamListener(controller);

		//コンフィグ設定
		getSaveData().setDefaultValue("OmikuziText", "おみくじが設定されていません\nおみくじが設定されていません");
		getSaveData().setDefaultValue("omikuzi", true);
	}

	@Override
	protected IJavatterTab getPluginConfigViewObserver(){
		return new OmikuziConfigView(getSaveData());
	}

	@Override
	public String getPluginName() {
		return "おみくじ";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}
