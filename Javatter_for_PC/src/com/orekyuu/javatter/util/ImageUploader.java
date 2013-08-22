package com.orekyuu.javatter.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import com.orekyuu.javatter.view.MainWindowView;
import com.orekyuu.javatter.viewobserver.ImagePreviewViewObserber;

public class ImageUploader implements DropTargetListener{

	private TwitterUtil twitter;
	private ImagePreviewViewObserber preview;

	public ImageUploader(TwitterUtil util, MainWindowView mainWindowView){
		twitter=util;
		preview=mainWindowView;
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {

	}

	@Override
	public void dragExit(DropTargetEvent dte) {

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent dtde) {
		Transferable transfer=dtde.getTransferable();

		if(transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			try {
				List<File> file=(List<File>) transfer.getTransferData(DataFlavor.javaFileListFlavor);
				for(File f : file){
					uploadImage(f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void uploadImage(final File file){
		if(!(file.getName().endsWith(".png")||file.getName().endsWith(".jpg")||file.getName().endsWith(".gif"))){
			System.out.println(file.getName()+"はアップロードできません");
		}
		twitter.setImage(file);
		final ImagePreviewViewObserber observer = this.preview;
		Thread th = new Thread()
		{
			public void run() {
				observer.change(file);
			}
		};
		th.start();
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

}
