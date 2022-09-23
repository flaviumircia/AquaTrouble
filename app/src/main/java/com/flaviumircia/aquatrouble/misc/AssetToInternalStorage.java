package com.flaviumircia.aquatrouble.misc;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetToInternalStorage {
    private Context context;
    public AssetToInternalStorage(Context context){
        this.context=context;
    }

    public void copyAsset(String filename,String dirPath){
        File dir=new File(dirPath);

        if(!dir.exists()){
            dir.mkdir();
        }

        AssetManager assetManager= context.getAssets();
        InputStream in=null;
        OutputStream out=null;

        try{

            in=assetManager.open(filename);
            File outfile=new File(dirPath,filename);
            out= new FileOutputStream(outfile);
            copyFile(in,out);
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(context, "Failed to save map!", Toast.LENGTH_SHORT).show();
        }finally {
            if(in!=null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private void copyFile(InputStream is,OutputStream out) throws IOException{
        byte[] buffer=new byte[1024];
        int read;
        while((read= is.read(buffer))!=-1){
            out.write(buffer,0,read);
        }
    }
}
