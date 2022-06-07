package com.mpas.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.zxing.client.android.Intents;
import com.mpas.mvp.ui.main.CpmFragment;
import com.mpas.mvp.ui.main.MainFragment;
import com.mpas.mvp.ui.main.ManagementFragment;
import com.mpas.mvp.ui.main.PaymentFragment;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private com.mpas.mvp.OnBackPressedListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            fragmentChange(1,new Bundle());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String qrData = data.getStringExtra(Intents.Scan.RESULT);
                Log.e(TAG, Objects.requireNonNull(qrData));

                if (qrData.length() == 0) {

                    Toast.makeText(getApplicationContext(), "바코드 인식 실패", Toast.LENGTH_LONG).show();

                } else {
                    try {
                        String[] strings = qrData.split("=");
                        Pattern pattern = Pattern.compile("^(.+)://(.+)=(.+)$");
                        Matcher matcher = pattern.matcher(qrData);
                        String uid = strings[1];
                        Log.e("MID confirm", uid);
                        //ClientID parser
                        String[] midParse = qrData.split(";");
                        String _uid = midParse[1];
                        Bundle bundle = new Bundle();
                        //bundle.putString("_uid",midParse[0]);
                        //Log.e("_UID confirm", midParse[0]);
                        bundle.putString("uid",uid);
                        bundle.putString("_uid",_uid);

                        fragmentChange(2,bundle);

                    } catch (Exception exception) {
                        exception.printStackTrace();
                        Toast.makeText(this, "PAYDA 바코드가 아닙니다.", Toast.LENGTH_SHORT).show();

                    }
                }

            } else
                Toast.makeText(getApplicationContext(), "바코드 종료", Toast.LENGTH_LONG).show();

        }
    }

    public void fragmentChange(int status,Bundle bundle) {

        Fragment fragment = null;

        switch (status){
            case 1:
                fragment = MainFragment.newInstance(); // common Test
                //fragment = BlankFragment.newInstance(); // seoulPay
                //fragment = bipleFragment.newInstance(); // zeroPay
                break;
            case 2:
                fragment = PaymentFragment.newInstance();
                break;
            case 3:
                fragment = CpmFragment.newInstance();
                break;
            case 4:
            case 5:
                fragment = ManagementFragment.newInstance();
            default:
                break;
        }

        Objects.requireNonNull(fragment).setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment).commit();
    }

    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener; }

    @Override public void onBackPressed() { if(listener!=null){ listener.onBackPressed(); }else{ super.onBackPressed(); } }

}