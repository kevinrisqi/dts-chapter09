package polinema.ac.id.dtsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import polinema.ac.id.dtsapp.data.AppDbProvider;
import polinema.ac.id.dtsapp.data.User;
import polinema.ac.id.dtsapp.data.UserDao;


public class ProfileActivity extends AppCompatActivity
{
    // Komponen
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private Button btnSave;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.loadData();
        this.initComponents();
    }

    private void loadData()
    {
        UserDao daoUser = AppDbProvider.getInstance(this).userDao();

        this.currentUser = daoUser.selectOne();
    }

    private void initComponents()
    {
        this.edtUsername = this.findViewById(R.id.edt_username);
        this.edtPassword = this.findViewById(R.id.edt_password);
        this.edtEmail = this.findViewById(R.id.edt_email);
        this.edtPhoneNumber = this.findViewById(R.id.edt_phone_number);
        this.btnSave = this.findViewById(R.id.btn_save);

        if (currentUser == null){
            this.btnSave.setEnabled(false);
            return;
        }
        this.edtUsername.setText(this.currentUser.username);
        this.edtPassword.setText(this.currentUser.password);
        this.edtEmail.setText(this.currentUser.email);
        this.edtPhoneNumber.setText(this.currentUser.phoneNumber);
    }

    private void syncData(){
        this.currentUser.username = this.edtUsername.getText().toString();
        this.currentUser.password = this.edtPassword.getText().toString();
        this.currentUser.email = this.edtEmail.getText().toString();
        this.currentUser.phoneNumber = this.edtPhoneNumber.getText().toString();
    }

    public void onBtnSave_Click(View view)
    {
        this.syncData();

        UserDao daoUser = AppDbProvider.getInstance(this).userDao();

        daoUser.update(this.currentUser);

        Toast.makeText(this, "Your data has been updated!", Toast.LENGTH_SHORT).show();
    }

    public void onTxvDeleteAccount_Click(View view)
    {
        this.syncData();

        UserDao daoUser = AppDbProvider.getInstance(this).userDao();

        daoUser.delete(this.currentUser);

        Toast.makeText(this, "Your data has been deleted..", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),WelcomeBackActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
