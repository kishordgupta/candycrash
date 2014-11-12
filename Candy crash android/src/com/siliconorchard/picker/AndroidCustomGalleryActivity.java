package com.siliconorchard.picker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.siliconorchard.myowncandy.Menu;
import com.siliconorchard.myowncandy.R;
import com.siliconorchard.myowncandy.mystatic.Constant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class AndroidCustomGalleryActivity extends Activity {
	public int count;
	public Bitmap[] thumbnails;
	public boolean[] thumbnailsselection;
	public String[] arrPath;
	public ImageAdapter imageAdapter;
	public static ArrayList<String> sel;
	public static ArrayList<File> impfile;
	ProgressDialog progressDialog ;
	public  GridView imagegrid;
	public Context c =null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		c=this;
        imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
		imageAdapter = new ImageAdapter();
		new readtext().execute("");
	/*	final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media._ID;
		Cursor imagecursor = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy);
		int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
		this.count = imagecursor.getCount();
		this.thumbnails = new Bitmap[this.count];
		this.arrPath = new String[this.count];
		this.thumbnailsselection = new boolean[this.count];
		for (int i = 0; i < this.count; i++) {
			imagecursor.moveToPosition(i);
			int id = imagecursor.getInt(image_column_index);
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
			thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
					getApplicationContext().getContentResolver(), id,
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			arrPath[i]= imagecursor.getString(dataColumnIndex);
		}
		GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		imagecursor.close();
		progressDialog.dismiss();*/
		final Button selectBtn = (Button) findViewById(R.id.selectBtn);
		selectBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final int len = thumbnailsselection.length;
				int cnt = 0;
				 sel =new ArrayList<String>();
				 impfile=new ArrayList<File>();
				String selectImages = "";
				for (int i =0; i<len; i++)
				{
					
					if (thumbnailsselection[i]){
						
						sel.add(arrPath[i]);
						selectImages = selectImages + arrPath[i] + "|";
						
						cnt++;
					}
				}
				if (cnt == 0){
					Toast.makeText(getApplicationContext(),
							"Please select at least one image",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"You've selected Total " + cnt + " image(s).",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
				}
				if(cnt>=6)
				{   
					try{System.gc();}catch(Exception e){}
					int i=0;
					for (String string : sel ) {
						i++;
						File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
						Bitmap b= BitmapFactory.decodeFile(string);
						Bitmap out = Bitmap.createScaledBitmap(b, 100, 100, false);

						            File file = new File(dir, i+"resize.png");
						            FileOutputStream fOut;
						            try {
						                fOut = new FileOutputStream(file);
						                out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
						                fOut.flush();
						                fOut.close();
						                b.recycle();
						                out.recycle();

						            } catch (Exception e) { // TODO

						            }
						            impfile.add(file);
						Log.d("kd", ""+string);
					}
					//cnt=0;
			/*	//	Constant.ITEM_NAME_JEWEL =selectImages.split("|"); /*new String[] {
				            "diamond.png", "hexagon.png", "romb.png", "sphere.png",
				            "square.png", "triangle.png" };*/
					Intent mIntent = new Intent(getApplicationContext(), Menu.class);
					startActivity(mIntent);
					finish();
				}
			}
		});
	}

	class readtext extends AsyncTask<String, String, String> {
		 
        // Show Progress bar before downloading Music
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            progressDialog = ProgressDialog.show(c, "", "Loading...", true, false);
    		progressDialog.show();
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {
        	
        	final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
    		final String orderBy = MediaStore.Images.Media._ID;
    		Cursor imagecursor = getContentResolver().query(
    				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
    				null, orderBy);
    		int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
    		count = imagecursor.getCount();
    		thumbnails = new Bitmap[count];
    		arrPath = new String[count];
    		thumbnailsselection = new boolean[count];
    		for (int i = 0; i < count; i++) {
    			imagecursor.moveToPosition(i);
    			int id = imagecursor.getInt(image_column_index);
    			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
    			thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
    					getApplicationContext().getContentResolver(), id,
    					MediaStore.Images.Thumbnails.MICRO_KIND, null);
    			arrPath[i]= imagecursor.getString(dataColumnIndex);
    		}
    	
    		
    		imagecursor.close();
			return "";
    		
        }

       

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
        	imagegrid.setAdapter(imageAdapter);
        	progressDialog.dismiss();
        }
    }
	
	
	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.galleryitem, null);
				holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
				holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
				
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.checkbox.setId(position);
			holder.imageview.setId(position);

			holder.checkbox.setVisibility(View.GONE);
			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id = v.getId();

					if (thumbnailsselection[id]) {
						//v.setBackgroundColor(Color.WHITE);
						thumbnailsselection[id] = false;

						
					} else {
						//v.setBackgroundColor(Color.RED);

						ImageView a = (ImageView) v;
						a.setImageResource(R.drawable.ic_launcher);
						thumbnailsselection[id] = true;
					}
				}
			});
			holder.imageview.setImageBitmap(thumbnails[position]);
			if(	thumbnailsselection[position])
			{
				holder.imageview.setImageResource(R.drawable.ic_launcher);
			}
			holder.checkbox.setChecked(thumbnailsselection[position]);
			holder.id = position;
			return convertView;
		}
	}
	class ViewHolder {
		ImageView imageview;
		CheckBox checkbox;
		int id;
	}
	
	
}