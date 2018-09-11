package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomNumber {

	// ��ͨ�������������λ���֣�Ȼ�������λ��������ͼƬ����Ӧ���ͻ��ˣ�����
	public ValidateCode generateImage() {

		String Rands = "";
		Random random = new Random();

		// ����ѭ�������ĸ������
		for (int i = 0; i < 4; i++) {
			
			String rand = String.valueOf(random.nextInt(10));

			Rands += rand;
		}

		// ����ͼ��
		int width = 80;
		int height = 30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// ���ͼ��������getGraphics()��ͼ
		Graphics g = image.getGraphics();

		// �趨����ɫ
		g.setColor(getRandColor(100, 250));

		// ��һ������
		g.fillRect(0, 0, width, height);

		// �趨����
		g.setFont(new Font("Timess New Roman", Font.PLAIN, 28));

		// �������155�������ߣ�ʹͼ���е����ֲ��ױ���������̽�⵽
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, xl, yl);
		}

		// ������ɫ
		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		// ����֤����ʾ��ͼ����
		g.drawString(Rands, 10, 26);

		// ��ͼ�����ͷ�
		g.dispose();

		// ����ͼƬ�ŵ������ ���ص�����
		ValidateCode vc = new ValidateCode();
		vc.setImage(image);
		vc.setRand(Rands);

		return vc;
	}

	// ������Χ��������ɫ
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

	// �õ�����ַ�
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
