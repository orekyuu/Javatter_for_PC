package com.orekyuu.javatter.viewobserver;

import com.orekyuu.javatter.controller.ConfigController;
import com.orekyuu.javatter.model.ConfigModel;

public interface ConfigViewObserver {

	public void setConfigController(ConfigController controller);

	public void update(ConfigModel model);
}
