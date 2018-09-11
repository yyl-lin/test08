package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomNumber {

	// 先通过随机数生成四位数字，然后把这四位数字生成图片，响应到客户端，返回
	public ValidateCode generateImage() {

		String Rands = "";
		Random random = new Random();

		// 利用循环生成四个随机数
		for (int i = 0; i < 4; i++) {
			
			String rand = String.valueOf(random.nextInt(10));

			Rands += rand;
		}

		// 创建图像
		int width = 80;
		int height = 30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获得图形上下文getGraphics()绘图
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(getRandColor(100, 250));

		// 画一个矩形
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Timess New Roman", Font.PLAIN, 28));

		// 随机生成155条干扰线，使图像中的数字不易被其他程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, xl, yl);
		}

		// 设置颜色
		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		// 将验证码显示到图像中
		g.drawString(Rands, 10, 26);

		// 绘图工具释放
		g.dispose();

		// 生成图片放到对象里， 返回到对象
		ValidateCode vc = new ValidateCode();
		vc.setImage(image);
		vc.setRand(Rands);

		return vc;
	}

	// 给定范围获得随机颜色
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	// 得到随机字符
	public String randomStr(int n) {
		String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String str2 = "";
		int len = str1.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			str2 = str2 + str1.charAt((int) r);
		}
		return str2;
	}
}
