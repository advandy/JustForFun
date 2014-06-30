package com.example.justforfun;

import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoadImageFromURL {

	public Bitmap loadImageFromURL(String url){
		try {
	        URL imageURL = new URL(url);
			HttpGet httpRequest = null;

	        httpRequest = new HttpGet(imageURL.toURI());

	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response = (HttpResponse) httpclient
	                .execute(httpRequest);

	        HttpEntity entity = response.getEntity();
	        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
	        InputStream input = b_entity.getContent();

	        Bitmap bitmap = BitmapFactory.decodeStream(input);
	        return bitmap;

	    } catch (Exception ex) {
	    	ex.printStackTrace();
           return null;
	    }
	}
}
