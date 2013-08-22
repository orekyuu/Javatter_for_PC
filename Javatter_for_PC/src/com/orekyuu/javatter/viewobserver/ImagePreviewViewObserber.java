package com.orekyuu.javatter.viewobserver;

import java.io.File;

public interface ImagePreviewViewObserber {
	public abstract void change(File paramFile);

	public abstract void clear();
}
