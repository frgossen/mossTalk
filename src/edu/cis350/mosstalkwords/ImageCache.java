package edu.cis350.mosstalkwords;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;

/*
 * Cache of the images as bitmaps
 */

public class ImageCache {
	private static ImageCache imc;

	private LruCache<String, Bitmap> imCache;

	private ImageCache() {
		// set up cache for images
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// use 1/2 size of available memory for cache 
		final int cacheSize = maxMemory / 2;
		imCache = new LruCache<String, Bitmap>(cacheSize) {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	public static ImageCache getInstance() {
		if (imc == null)
			imc = new ImageCache();
		return imc;
	}

	public void addBitmapToCache(String key, Bitmap bitmap) {
		if (getBitmapFromCache(key) == null) {
			imCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromCache(String key) {
		return imCache.get(key);
	}

	public void clearCache(String[] words) {
		for (String w : words) {
			Bitmap b = imCache.get(w);
			if (b != null) {
				b.recycle();
			}
		}

		imCache.evictAll();
	}

	// scale down images based on display size; helps with OOM errors
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 0;
		int newHeight = height;
		int newWidth = width;

		while (newHeight > reqHeight || newWidth > reqWidth) {
			newHeight = newHeight / 2; // should be power of two
			newWidth = newWidth / 2;
			inSampleSize += 2;
		}
		if (inSampleSize == 0) {
			inSampleSize = 2;
		}
		Log.d("async task", "in sample size is:" + (inSampleSize));

		return inSampleSize;
	}

}
