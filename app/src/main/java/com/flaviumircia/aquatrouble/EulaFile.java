package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EulaFile {

    private Context context;
    public EulaFile(Context context)
    {
        this.context=context;
    }
    public List<String> readLine(String path)
    {
        List<String> mLines=new ArrayList<>();
        AssetManager am=context.getAssets();

        try
        {
            InputStream is=am.open(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = bufferedReader.readLine())!=null)
            {
                Log.d("TAG", "readLine: "+ line);
                mLines.add(line);
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    return mLines;
    }
}
