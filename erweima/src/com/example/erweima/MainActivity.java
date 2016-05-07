package com.example.erweima;
import java.util.Hashtable;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends Activity {
	EditText et;
ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et=(EditText) findViewById(R.id.et);
		iv=(ImageView) findViewById(R.id.iv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
public void create(View v) {
	
	try {
		QRCodeWriter writer = new QRCodeWriter();

		String text = et.getText().toString();
		if (text == null || "".equals(text) || text.length() < 1) {
			return;
		}

		int QR_HEIGHT = 400;
		int QR_WIDTH = 400;
		BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
				QR_WIDTH, QR_HEIGHT);			
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new QRCodeWriter().encode(text,
				BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
		int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
		
		Bitmap[] bitmaps=new Bitmap[2];
		bitmaps[1]=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		int imageW = bitmaps[1].getWidth();
		int imageH = bitmaps[1].getHeight();
		int startW=QR_WIDTH/2-imageW/2;
		int starH=QR_HEIGHT/2-imageH/2;
		for (int y = 0; y < QR_HEIGHT; y++) {
			for (int x = 0; x < QR_WIDTH; x++) {
				if ((x<=startW||x>=starH+imageW)||(y<=starH||y>=+imageH)) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} 
				} else {					
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
				Bitmap.Config.ARGB_8888);

		bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
		
		
		bitmaps[0]=bitmap;
		
		iv.setImageBitmap(combineBitmaps(bitmaps, startW,starH));
//		qr_image.setImageBitmap(bitmap);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	
	
	
	
//	 try {
//         // 需要引入core包
//         QRCodeWriter writer = new QRCodeWriter();
//
//         String text = et.getText().toString();
//
//         
//         if (text == null || "".equals(text) || text.length() < 1) {
//             return;
//         }
//
//         int QR_WIDTH = 300;
//		int QR_HEIGHT = 200;
//		// 把输入的文本转为二维码
//         BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
//                 QR_WIDTH, QR_HEIGHT);
//
//         System.out.println("w:" + martix.getWidth() + "h:"
//                 + martix.getHeight());
//
//         Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//         BitMatrix bitMatrix = new QRCodeWriter().encode(text,
//                 BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
//         int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
//         for (int y = 0; y < QR_HEIGHT; y++) {
//             for (int x = 0; x < QR_WIDTH; x++) {
//                 if (bitMatrix.get(x, y)) {
//                     pixels[y * QR_WIDTH + x] = 0xff000000;
//                 } else {
//                     pixels[y * QR_WIDTH + x] = 0xffffffff;
//                 }
//
//             }
//         }
//
//         Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
//                 Bitmap.Config.ARGB_8888);
//
//         bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
//         iv.setImageBitmap(bitmap);
//
//     } catch (WriterException e) {
//         e.printStackTrace();
//     }
	
}

	public static Bitmap combineBitmaps(Bitmap[] bitmaps,int w,int h) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmaps[0].getWidth(),
				bitmaps[0].getHeight(), Config.ARGB_8888);
		Canvas cv = new Canvas(newBitmap);	
		for (int i = 0; i < bitmaps.length; i++) {
			if (i == 0) {
				cv.drawBitmap(bitmaps[0], 0, 0, null);
			} else {				
				cv.drawBitmap(bitmaps[i], w, h, null);
			}
			cv.save(Canvas.ALL_SAVE_FLAG);
			cv.restore();
		}
		return newBitmap;
	
	
}
}
