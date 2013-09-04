package com.orekyuu.javatter.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import com.orekyuu.javatter.view.MainWindowView;
import com.orekyuu.javatter.viewobserver.ImagePreviewViewObserber;

/**
 * 画像をアップロードするクラス
 * @author orekyuu
 *
 */
public class ImageUploader implements DropTargetListener{

	private TwitterUtil twitter;
	private ImagePreviewViewObserber preview;
	private HashSet<String> allowExtension = new HashSet<String>();

	/**
	 * @param util TwitterUtil
	 * @param mainWindowView メインウィンドウ
	 */
	public ImageUploader(TwitterUtil util, MainWindowView mainWindowView){
		twitter=util;
		preview=mainWindowView;
		allowExtension.add("jpg");
		allowExtension.add("png");
		allowExtension.add("gif");
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

	private String getExtension(File file){
		if(file == null)
			return null;
		String path=file.getPath();
		int point=path.lastIndexOf(".");
		if(point!=-1){
			return path.substring(point+1);
		}
		return null;
	}
	
	private boolean isAllowExtension(String extension){
		if(extension == null)
			return false;
		String ext = extension.toLowerCase();
		if(allowExtension.contains(ext))
			return true;
		return false;
	}
	
	private void uploadImage(final File file){
		if(!isAllowExtension(getExtension(file))){
			String title = "ImageUploader";
			String message = file.getName()+"はアップロードできません";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			return;
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
