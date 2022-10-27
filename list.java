package com.example.subham.museu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;




import java.util.ArrayList;

import static android.widget.Toast.makeText;



public class List extends AppCompatActivity {


    private static final int MY_PERMISSION_REQUEST =1;//
    String aru;


    ArrayList<String> arrayList;//
    ArrayList<String> arrayList1;
    public String arr[];
    ListView listView;//
    ArrayAdapter<String> adapter;//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(ContextCompat.checkSelfPermission(List.this,//
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){//
            if(ActivityCompat.shouldShowRequestPermissionRationale(List.this,//
                    Manifest.permission.READ_EXTERNAL_STORAGE)){//
                ActivityCompat.requestPermissions(List.this,//
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);//
            } else {//
                ActivityCompat.requestPermissions(List.this,//
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);//
            }
        } else {//
            doStuff();//
        }



    }

    public void doStuff(){//
        listView= (ListView) findViewById(R.id.listView);//
        arrayList=new ArrayList<>();//
        getMusic();//
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);//
        listView.setAdapter(adapter);//
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//
                // long s=  adapterView.getSelectedItemId();//open music player to play desired song
                String s=arrayList.get(i);
                //Toast.makeText(MainActivity.this,""+ s,Toast.LENGTH_SHORT).show();
                String aru1=arrayList.get(i);
                //Toast.makeText(MainActivity.this,""+ aru,Toast.LENGTH_SHORT).show();
                int a=aru1.lastIndexOf('\n');
                aru=aru1.substring(a+1);

                final int port = 15123;
                final boolean isRunning = true;
                //final TextView textView=findViewById(R.id.text);

                //Toast.makeText(this, "i am a student", Toast.LENGTH_SHORT).show();


                //Toast.makeText(this, "i am a nice boy", Toast.LENGTH_SHORT).show();


                final Thread thread = new Thread(new Runnable() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            //Toast.makeText(MainActivity.this, "i am a good boy", Toast.LENGTH_SHORT).show();
                            ServerSocket serverSocket = new ServerSocket(port);
                            while (isRunning) {
                                // Toast.makeText(MainActivity.this, "i am a boy", Toast.LENGTH_SHORT).show();
                                //System.out.print("inside while");
                                Socket socket = serverSocket.accept();



                                // isRunning=false;
                                //Toast.makeText(MainActivity.this, "i am a girl", Toast.LENGTH_SHORT).show();
                                //isRunning=false;
                                handle(socket);
                                //break;
                            }
                        } catch (IOException e) {
                            //Toast.makeText(MainActivity.this, "i am a bad boy", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();










            }
        });
    }
    public void getMusic(){//
        ContentResolver contentResolver=getContentResolver();//
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//
        // Toast.makeText(this,""+songUri, Toast.LENGTH_LONG).show();
        Cursor songCursor=contentResolver.query(songUri,null,null,null,null);

        if(songCursor!=null && songCursor.moveToFirst()){//
            int i=0;
            int songTitle= songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);//
            int songArtist= songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);//
            int songLocation= songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);//
            int z=songCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
            do{
                String currentTitle=songCursor.getString(songTitle);//
                String currentArtist=songCursor.getString(songArtist);//
                String currentLocation=songCursor.getString(songLocation);//
                //String zz=songCursor.getString(z);
                //aru=currentLocation;
                arrayList.add("Title: "+currentTitle+"\n"+"Artist: "+currentArtist+"\n "+"Location: "+"\n"+currentLocation);
                // arrayList1.add(currentLocation);
                //String currentLocation1=songCursor.getString(songLocation);
                //arr[i]=currentLocation;
                //i++;
            }while (songCursor.moveToNext());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {//
        switch (requestCode){//
            case MY_PERMISSION_REQUEST:{//
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){//
                    if(ContextCompat.checkSelfPermission(List.this,//
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){//
                        Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();//
                        doStuff();//
                    }
                } else {
                    Toast.makeText(this,"no permission granted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handle(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            String route = null;

            // Read HTTP headers and parse out the route.
            /*reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (!TextUtils.isEmpty(line = reader.readLine())) {
                if (line.startsWith("GET /")) {
                    int start = line.indexOf('/') + 1;
                    int end = line.indexOf(' ', start);
                    route = line.substring(start, end);
                    break;
                }
            }*/

            // Output stream that we send the response to
            //Path path = Paths.get("/sdcard/Download/03 - Khalibali - DownloadMing.SE.mp3");
            //byte[] data = Files.readAllBytes(path);
            File file=new File(aru);
            //byte[] org.apache.commons.io.FileUtils.readFileToByteArray(file);
            RandomAccessFile f = new RandomAccessFile(file, "r");
            byte[] data=new byte[(int) f.length()];
            f.readFully(data);
            output = new PrintStream(socket.getOutputStream());

            // Prepare the content to send.
           /* if (null == route) {
                writeServerError(output);
                return;
            }*/
            //byte[] bytes = loadContent(route);
            //Toast.makeText(this,"i am a boy",Toast.LENGTH_SHORT).show();
            //byte[] bytes=null;
            /*if (null == bytes) {
                writeServerError(output);
                return;
            }*/
            int b=data.length;
            String s=Integer.toString(b);
            byte[] bytes="hello world".getBytes();
            // Send out the content.
            output.write(("HTTP/1.1 200 OK\r\nContent-Type: audio/mpeg\r\nContent-Legnth: " +data.toString().length()+"\r\n\r\n").getBytes("ISO-8859-1"));
            // output.println("Content-Type: " + detectMimeType(route));
           /* output.write(("Content-Type: " + "text/plain\r\n").getBytes("ISO-8859-1"));
            output.write(("Content-Length: " + data.toString().length()+"\r\n").getBytes("ISO-8859-1"));
            output.write(("\r\n").getBytes("ISO-8859-1"));*/
            output.write(data);
            output.write(("\r\n").getBytes("ISO-8859-1"));
            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != output) {
                output.close();
            }
            if (null != reader) {
                reader.close();
            }
        }
        socket.close();
    }

    private String detectMimeType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        } else if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else {
            return "application/octet-stream";
        }
    }

    private byte[] loadContent(String fileName) {
        InputStream input = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            AssetManager mAssets = null;
            input = mAssets.open(fileName);
            byte[] buffer = new byte[1024];
            int size;
            while (-1 != (size = input.read(buffer))) {
                output.write(buffer, 0, size);
            }
            output.flush();
            return output.toByteArray();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeServerError(PrintStream output) {
        output.println("HTTP/1.0 500 Internal Server Error");
        output.flush();
    }



}

