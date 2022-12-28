package com.mpas.mvp.management.ui.payments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mpas.mvp.R;
import com.mpas.mvp.management.ui.scanner.ScanActivity;

import java.util.Objects;

public class PayFactoryActivity extends AppCompatActivity {

    String TAG = PayFactoryActivity.class.getSimpleName();
    private static NavController navController;
    private PaymentsViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_factory);

        vm =  new ViewModelProvider(this).get(PaymentsViewModel.class);
        //navController = Navigation.findNavController(this,R.id.nav_host_settings);

        Log.e(TAG,"onCreate");
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);//바코드 인식시 소리
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.setPrompt("찰칵"); // ??
        intentIntegrator.setCameraId(0); // ?? 전면 1, 후면 0
        intentIntegrator.setRequestCode(100);
        intentIntegrator.setTimeout(60000);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("zxing", "333");

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String qrData = Objects.requireNonNull(data).getStringExtra(Intents.Scan.RESULT);
                Log.e(TAG, Objects.requireNonNull(qrData));

                if (qrData.length() < 16) {

                    Toast.makeText(getApplicationContext(), "잘못된 바코드 입니다. 길이 오류", Toast.LENGTH_LONG).show();

                } else {
                    try {
/*                        String[] strings = qrData.split("=");
                        Pattern pattern = Pattern.compile("^(.+)://(.+)=(.+)$");
                        Matcher matcher = pattern.matcher(qrData);
                        String uid = strings[1];

                        //ClientID parser
                        String[] midParse = uid.split(";");
                        String _uid = midParse[1];
                        Bundle bundle = new Bundle();
                        bundle.putString("_uid",midParse[0]);
                        bundle.putString("uid",uid);
                        //bundle.putString("_uid",_uid);

                        fragmentChange(2,bundle);*/

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

            } else
                Toast.makeText(getApplicationContext(), "바코드 종료", Toast.LENGTH_LONG).show();

        }
    }
}