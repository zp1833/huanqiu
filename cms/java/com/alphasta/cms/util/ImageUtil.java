package com.alphasta.cms.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alphasta.common.Constants;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;

public class ImageUtil {
	private static String copyFile = Constants.COPYRIGHT_IMAGE;

	// private static void toJPG(String source, String dest, int quality) {
	//
	// if (dest == null || dest.trim().equals(""))
	// dest = source;
	//
	// if (!dest.toLowerCase().trim().endsWith("jpg")) {
	// dest += ".jpg";
	// System.out.println("Overriding to JPG, output file: " + dest);
	// }
	// if (quality < 0 || quality > 100 || (quality + "") == null
	// || (quality + "").equals("")) {
	// System.out.println("quality must between ’0’ and ’100’");
	// System.out.println("set to DEFAULT value:’75’");
	// quality = 75;
	//
	// }
	// try {
	// JPGOptions options = new JPGOptions();
	// options.setQuality(quality);
	// ImageProducer image = Jimi.getImageProducer(source);
	// JimiWriter writer = Jimi.createJimiWriter(dest);
	// writer.setSource(image);
	// // 加入属性设置，非必要
	// // /*
	// writer.setOptions(options);
	// // */
	// writer.putImage(dest);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// private void toGIF(String source, String dest) throws JimiException {
	// if (dest == null || dest.trim().equals(""))
	// dest = source;
	// // 1:转换为jpg
	// if (!dest.toLowerCase().trim().endsWith("jpg")) {
	// dest += ".jpg";
	// }
	// toJPG(source, dest, 75);
	//
	// BufferedImage file_in = null;
	// File file = new File(dest);
	// try {
	// file_in = javax.imageio.ImageIO.read(file);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// int end = dest.lastIndexOf(".");
	// file.deleteOnExit();
	// // output *.gif
	// file.renameTo(new File(dest.substring(0, end) + ".gif"));
	// // jpg to gif
	// AnimatedGifEncoder e = new AnimatedGifEncoder();
	// e.start(dest);
	// e.addFrame(file_in);
	// e.finish();
	//
	// }

	/**
	 * 
	 * @param img
	 * @param dest
	 * @throws JimiException
	 */
	// private static void toTIF(Image img, String dest) {
	// try {
	// if (!dest.toLowerCase().trim().endsWith("tif")) {
	// dest += ".tif";
	// System.out.println("Overriding to TIF, output file: " + dest);
	// }
	// dest = dest.substring(0, dest.lastIndexOf(".")) + ".jpg";
	// JimiWriter writer = Jimi.createJimiWriter(dest);
	// writer.setSource(img);
	// dest = dest.substring(0, dest.lastIndexOf(".")) + ".tif";
	// writer.putImage(dest);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	/**
	 * 线性改变图片尺寸(可同时改变图片格式)
	 * 
	 * @param source
	 *            源文件完整路径
	 * @param desc
	 *            目标文件完整路径
	 * @param nWidth
	 *            指定宽度
	 * @param nHeight
	 *            指定高度
	 * @throws JimiException
	 * @throws IOException
	 */
	public static void changeDimension(String serverPath, String source,
			String desc, String nWidth, String nHeight, String nBorder,
			String addImage) {
		try {
			String temp = desc;
			File _file = null;
			_file = new File(source); // 读入文件

			Image src = javax.imageio.ImageIO.read(_file); // 构造Image对象
			double wideth = (double) src.getWidth(null); // 得到源图宽
			double height = (double) src.getHeight(null); // 得到源图长
			int iWidth = (int) wideth;
			int iHeight = (int) height;
			if (nWidth != null && !"".equals(nWidth.trim())
					&& !"0".equals(nWidth.trim())) {
				iWidth = Integer.valueOf(nWidth.trim());
			} else {
				nWidth = "0";
			}
			if (nHeight != null && !"".equals(nHeight.trim())
					&& !"0".equals(nHeight.trim())) {
				iHeight = Integer.valueOf(nHeight.trim());
			} else {
				nHeight = "0";
			}
			if (nBorder != null
					&& (!"".equals(nBorder.trim()) && !"0".equals(nBorder
							.trim()))
					&& ("0".equals(nWidth) && "0".equals(nHeight))) {
				iHeight = iHeight * Integer.valueOf(nBorder.trim()) / 100;
				iWidth = iWidth * Integer.valueOf(nBorder.trim()) / 100;
			}

			// 判断图片是否太宽，若太宽则缩小
			if (iWidth > Constants.GLOBAL_IMAGE_WIDTH) {
				double percent = Constants.GLOBAL_IMAGE_WIDTH / (double) iWidth;
				iWidth = Constants.GLOBAL_IMAGE_WIDTH;
				iHeight = (int) (iHeight * percent);
			}

			BufferedImage tag = new BufferedImage(iWidth, iHeight,
					BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(src, 0, 0, iWidth, iHeight, null); // 绘制缩小后的图

			if ("1".equals(addImage)) {
				// 打水印
				File _filebiao = new File(serverPath + copyFile);
				if (_filebiao.exists() && _filebiao.isFile()) {
					Image src_biao = ImageIO.read(_filebiao);
					int wideth_biao = src_biao.getWidth(null);
					int height_biao = src_biao.getHeight(null);
					Graphics2D g = tag.createGraphics();
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_ATOP, 0.5f));// 设置透明度
					g.drawImage(src_biao, iWidth - wideth_biao, iHeight
							- height_biao, null);
					g.dispose();
				}
			}
			if (!temp.trim().equals(desc))
				_file.deleteOnExit();

			if (desc.toLowerCase().trim().endsWith("gif")) {
				AnimatedGifEncoder e = new AnimatedGifEncoder();
				e.start(desc);
				e.addFrame(tag);
				e.finish();
			} else {
				JimiWriter writer = Jimi.createJimiWriter(desc);
				writer.setSource(tag);
				writer.putImage(desc);
			}
			File _temp = new File(source);
			if (_temp.exists())
				_temp.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
