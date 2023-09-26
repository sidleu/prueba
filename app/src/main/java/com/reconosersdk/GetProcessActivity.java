package com.reconosersdk;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CancelarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.Ciudadano;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarEstadoProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ProcesosPendientes;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.SolicitudProceso;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.AutenticarAsesorOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.CancelarProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarEstadoProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarProcesoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.MotivosCanceladoOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ProcesosPendientesOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SedeConvenioOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitudProcesoOut;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.JsonUtils;
import com.reconosersdk.utils.Utils;

import java.util.Calendar;
import java.util.Date;

public class GetProcessActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Every services to implement and use
    private static final String AUTENTICAR_ASESOR = "AUTENTICAR ASESOR";
    private static final String SOLICITUD_PROCESO = "SOLICITUD PROCESO";
    private static final String PROCESOS_PENDIENTES = "PROCESOS PENDIENTES";
    private static final String CANCELAR_PROCESO = "CANCELAR PROCESO";
    private static final String MOTIVOS_CANCELADO = "MOTIVOS CANCELADO";
    private static final String SEDE_CONVENIO = "SEDE CONVENIO";
    private static final String CONSULTAR_ESTADO_PROCESO = "CONSULTAR ESTADO PROCESO";
    private static final String CONSULTAR_PROCESO = "CONSULTAR PROCESO";

    //Spinner to select any option to Process
    private Spinner optionsProcess;
    private Spinner optionsHelper;
    //To input any text
    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private EditText editTextClientCode;
    private EditText editTextCC;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextPrefixCounry;
    private EditText editTextState;
    //To show the kind of service in every text
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    //To show the response
    private TextView result;
    //To send the data to service
    private Button button;
    //To show the progress
    public ProgressDialog mProgressDialog;

    //Kind of service
    private String selectedProcess;
    private int serviceProcess = 0;

    private LinearLayout linearLayout;

    //To use in Spinner

    //To set the date
    private DatePickerDialog.OnDateSetListener date;
    private String fechaInicial;
    private String fechaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_process);

        final Drawable yourIcon = getResources().getDrawable(R.drawable.back, null);
        getSupportActionBar().setHomeAsUpIndicator(yourIcon);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        ((TextView) v.findViewById(R.id.title)).setText(this.getTitle());
        getSupportActionBar().setCustomView(v);

        setSpinner();
        setEditText();
        setTextView();
        setClickProcess();
        setDatePicker();
    }

    private void setClickProcess() {
        button = findViewById(R.id.buttonProcess);
        button.setOnClickListener(view -> {
            loading(getString(R.string.loading));
            selectorService(serviceProcess);
        });
    }

    private void setEditText() {
        editText = findViewById(R.id.etx_guidCv);
        editText2 = findViewById(R.id.etx_nombre);
        editText3 = findViewById(R.id.etx_sede);
        editTextClientCode = findViewById(R.id.etx_CodCliente);
        editTextCC = findViewById(R.id.etx_numDoc);
        editTextEmail = findViewById(R.id.etx_email);
        editTextPhone = findViewById(R.id.etx_cellPhone);
        editTextPrefixCounry = findViewById(R.id.etx_prefix_country);
        editTextState = findViewById(R.id.etx_state);
    }

    private void setTextView() {
        textView = findViewById(R.id.tv_guidCv);
        textView2 = findViewById(R.id.tv_nombre);
        textView3 = findViewById(R.id.tv_sede);
        textView4 = findViewById(R.id.tv_numDoc);
        textView5 = findViewById(R.id.tv_email);
        textView6 = findViewById(R.id.tv_cellPhone);
        textView7 = findViewById(R.id.tv_state);
        linearLayout = findViewById(R.id.linear_process);
        result = findViewById(R.id.textViewProcess);
    }

    private void setDatePicker() {
        date = (view, year, monthOfYear, dayOfMonth) -> {
            Utils.MY_CALENDAR.set(Calendar.YEAR, year);
            Utils.MY_CALENDAR.set(Calendar.MONTH, monthOfYear);
            Utils.MY_CALENDAR.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(Utils.MY_CALENDAR.getTime());
        };
    }

    private void updateLabel(Date time) {
        fechaFinal = Utils.SDF.format(time);
        fechaInicial = Utils.deleteMonth(time);
        String currentDate = Utils.setedCurrentDate(fechaFinal, fechaInicial);
        editText3.setText(currentDate);
    }

    public void loading(String msgRes) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);

            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.setMessage(msgRes);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void selectorService(int serviceProcess) {
        switch (serviceProcess) {
            case 0:
                autenticarAsesor();
                break;
            case 1:
                solicitudProceso();
                break;
            case 2:
                procesosPendientes();
                break;
            case 3:
                cancelarProceso();
                break;
            case 4:
                motivosCancelado();
                break;
            case 5:
                sedeConvenio();
                break;
            case 6:
                consultarEstadoProceso();
                break;
            default:
                consultarProceso();
                break;
        }
    }

    private void setSpinner() {
        //Spinner Selected
        optionsProcess = findViewById(R.id.spinner);
        optionsHelper = findViewById(R.id.spinnerHelper);
        String[] options = getArrayString();
        ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, options);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);
        // attaching data adapter to spinner
        optionsProcess.setAdapter(dataAdapter);
        optionsProcess.setOnItemSelectedListener(this);
        //setSpinnerHelper();
    }

    //To easily test
    private String[] getArrayString() {
        return getApplicationContext().getResources().getStringArray(R.array.selected_process);
    }

    private void setSpinnerHelper() {
        String[] options = getArrayMotivation();
        ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.spinner_style, options);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        optionsHelper.setAdapter(dataAdapter);
        optionsHelper.setSelection(0);
        optionsHelper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editText3.setFocusable(true);
                editText3.setText(optionsHelper.getSelectedItem().toString()); //this is taking the first value of the spinner by default.
                optionsHelper.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No implementation needed
                editText3.setFocusable(true);
                optionsHelper.setVisibility(View.GONE);
            }
        });
    }

    //To easily test
    private String[] getArrayMotivation() {
        return getApplicationContext().getResources().getStringArray(R.array.selected_motivation);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        selectedProcess = adapterView.getItemAtPosition(position).toString();
        createServices(selectedProcess);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //No implementation needed
    }

    private void createServices(String selectedProcess) {
        switch (selectedProcess) {
            case AUTENTICAR_ASESOR:
                serviceProcess = 0;
                showFields("Nombre Asesor", "testing",
                        "Contraseña", "931135", "", "");
                editText.setHint("Nombre del asesor");
                editText2.setHint("Contraseña");
                break;
            case SOLICITUD_PROCESO:
                serviceProcess = 1;
                showFields("GuidConvenio", "97464498-D532-4964-ABF7-81DC0F884B21",
                        "Nombre Asesor", "testing", "Sede", "931135");
                editText.setHint("Escriba el convenio asociado");
                editText2.setHint("Nombre del asesor");
                editText3.setHint("Id de la sede");
                break;
            case PROCESOS_PENDIENTES:
                serviceProcess = 2;
                showFields("GuidConvenio", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Nombre Asesor", "testing", "Sede", "000100");
                editText.setHint("Escriba el convenio asociado");
                editText2.setHint("Nombre del asesor");
                editText3.setHint("Id de la sede");
                break;
            case CANCELAR_PROCESO:
                serviceProcess = 3;
                showFields("ProcesoConvenioGuid", "76C7F84E-F2BA-427D-A8C0-2541B0C84C89",
                        "Nombre Asesor", "testing", "Motivo", "No soportado");
                editText.setHint("Escriba el proceso del convenio");
                editText2.setHint("Nombre del asesor");
                editText3.setHint("Motivo de cancelación");
                break;
            case MOTIVOS_CANCELADO:
                serviceProcess = 4;
                showFields("GuidConvenio", "bfa3ab5f-baeb-42de-986e-0da98f45f90f",
                        "", "", "", "");
                editText.setHint("Escriba el convenio asociado");
                break;
            case SEDE_CONVENIO:
                serviceProcess = 5;
                showFields("GuidConvenio", "bfa3ab5f-baeb-42de-986e-0da98f45f90f",
                        "", "", "", "");
                editText.setHint("Escriba el convenio asociado");
                break;
            case CONSULTAR_ESTADO_PROCESO:
                serviceProcess = 6;
                editText3.setFocusable(false);
                String currentDate = Utils.setedCurrentDate(Utils.getToday(), Utils.deleteMonth(new Date()));
                showFields("GuidConvenio", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Nombre Asesor", "testing", "Fecha actual-hace un mes", currentDate);
                editText.setHint("Escriba el convenio asociado");
                editText2.setHint("Nombre del asesor");
                editText3.setHint("Fecha de consulta");
                break;
            case CONSULTAR_PROCESO:
                serviceProcess = 7;
                editText3.setFocusable(false);
                showFields("GuidConvenio", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "ProcesoConvenioGuid", "9ACD1888-A6E4-4820-8C61-12948BB754DA", "Código cliente", "10002");
                editText.setHint("Escriba el convenio asociado");
                editText2.setHint("Escriba el proceso del convenio");
                editText3.setHint("Escriba el código del cliente");
                break;
        }
        editTextFocus();
        result.setText("");
    }

    private void editTextFocus() {

        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (serviceProcess == 3) {
                    //optionsHelper.setVisibility(View.VISIBLE);
                    editText3.setFocusable(false);
                    optionsHelper.setVisibility(View.VISIBLE);
                    // Open the Spinner...
                    optionsHelper.setSelection(0);
                    optionsHelper.performClick();
                }*/
                if (serviceProcess == 6) {
                    editText3.setFocusable(false);
                    new DatePickerDialog(GetProcessActivity.this, date, Utils.MY_CALENDAR
                            .get(Calendar.YEAR), Utils.MY_CALENDAR.get(Calendar.MONTH),
                            Utils.MY_CALENDAR.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    optionsHelper.setVisibility(View.GONE);
                    editText3.setFocusable(true);
                }
            }
        });
    }

    private void showFields(String text1, String hint1, String text2, String hint2, String text3, String hint3) {
        if (text1 == null || text1.isEmpty()) {
            onHideField(textView, editText);
        } else {
            onShowFieldText(textView, editText, text1, hint1);
        }
        if (text2 == null || text2.isEmpty()) {
            onHideField(textView2, editText2);
        } else {
            onShowFieldText(textView2, editText2, text2, hint2);
        }
        if (text3 == null || text3.isEmpty()) {
            onHideField(textView3, editText3);
        } else {
            onShowFieldText(textView3, editText3, text3, hint3);
        }
    }

    private void onHideField(TextView textView, EditText editText) {
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
    }

    private void onShowFieldText(TextView textView, EditText editText, String text, String hint) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
        editText.setVisibility(View.VISIBLE);
        editText.setText(hint);
    }

    //To auth
    private void autenticarAsesor() {
        linearLayout.setVisibility(View.GONE);
        String adviser = editText.getText().toString();
        String codeAdviser = editText2.getText().toString();
        ServicesOlimpia.getInstance().authAdviser(adviser, codeAdviser, new OlimpiaInterface.CallbackAuthentication() {
            @Override
            public void onSuccess(AutenticarAsesorOut autenticarAsesorOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(autenticarAsesorOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });

    }

    //To get the campus
    private void sedeConvenio() {
        linearLayout.setVisibility(View.GONE);
        String guidCiu = editText.getText().toString();
        ServicesOlimpia.getInstance().getSedeConvenio(guidCiu, new OlimpiaInterface.CallbackSedeConvenio() {
            @Override
            public void onSuccess(SedeConvenioOut sedeConvenioOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(sedeConvenioOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }

    //To get the canceled reason
    private void motivosCancelado() {
        linearLayout.setVisibility(View.GONE);
        String guidCiu = editText.getText().toString();
        ServicesOlimpia.getInstance().getCanceledReason(guidCiu, new OlimpiaInterface.CallbackMotivosCancelado() {
            @Override
            public void onSuccess(MotivosCanceladoOut motivosCanceladoOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(motivosCanceladoOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }

    //To get the pending process
    private void procesosPendientes() {
        linearLayout.setVisibility(View.GONE);
        ProcesosPendientes pendientes = new ProcesosPendientes();
        pendientes.setAsesor(editText2.getText().toString());
        pendientes.setGuidConv(editText.getText().toString());
        pendientes.setSede(editText3.getText().toString());
        ServicesOlimpia.getInstance().getPendingProcess(pendientes, new OlimpiaInterface.CallbackProcesosPendientes() {
            @Override
            public void onSuccess(ProcesosPendientesOut procesosPendientesOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(procesosPendientesOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }

    //To cancel the process
    private void cancelarProceso() {
        linearLayout.setVisibility(View.GONE);
        CancelarProceso cancelarProceso = new CancelarProceso();
        cancelarProceso.setAsesor(editText2.getText().toString());
        cancelarProceso.setProcesoConvenioGuid(editText.getText().toString());
        cancelarProceso.setMotivo(editText3.getText().toString());
        ServicesOlimpia.getInstance().getCancelProcess(cancelarProceso, new OlimpiaInterface.CallbackCancelarProceso() {
            @Override
            public void onSuccess(CancelarProcesoOut cancelarProcesoOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(cancelarProcesoOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }

    //To consult the state process
    private void consultarEstadoProceso() {
        linearLayout.setVisibility(View.GONE);
        ConsultarEstadoProceso consultarEstadoProceso = new ConsultarEstadoProceso();
        consultarEstadoProceso.setAsesor(editText2.getText().toString());
        consultarEstadoProceso.setGuidConv(editText.getText().toString());
        consultarEstadoProceso.setFechaFinal(fechaFinal);
        consultarEstadoProceso.setFechaInicial(fechaInicial);
        ServicesOlimpia.getInstance().getConsultProcessState(consultarEstadoProceso, new OlimpiaInterface.CallbackConsultarEstadoProceso() {
            @Override
            public void onSuccess(ConsultarEstadoProcesoOut consultarEstadoProcesoOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(consultarEstadoProcesoOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }

    private void solicitudProceso() {
        linearLayout.setVisibility(View.VISIBLE);
        SolicitudProceso solicitudProceso = new SolicitudProceso();
        solicitudProceso.setAsesor( editText2.getText().toString());
        solicitudProceso.setGuidConv( editText.getText().toString());
        solicitudProceso.setSede( editText3.getText().toString());
        solicitudProceso.setCodigoCliente( editTextClientCode.getText().toString());
        solicitudProceso.setInfCandidato(null);
        solicitudProceso.setFinalizado(false);
        solicitudProceso.setEstado(Integer.parseInt(editTextState.getText().toString()));
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setTipoDoc("CC");
        ciudadano.setNumDoc(editTextCC.getText().toString());
        ciudadano.setEmail(editTextEmail.getText().toString());
        ciudadano.setCelular(editTextPhone.getText().toString());
        ciudadano.setPrefCelular(editTextPrefixCounry.getText().toString());
        solicitudProceso.setCiudadano(ciudadano);

        ServicesOlimpia.getInstance().getProcessRequest(solicitudProceso, new OlimpiaInterface.CallbackSolicitudProceso() {
            @Override
            public void onSuccess(SolicitudProcesoOut solicitudProcesoOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(solicitudProcesoOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }

    private void consultarProceso() {
        linearLayout.setVisibility(View.GONE);
        ConsultarProceso consultarProceso = new ConsultarProceso();
        consultarProceso.setGuidConv(editText.getText().toString());
        consultarProceso.setProcesoConvenioGuid(editText2.getText().toString());
        consultarProceso.setCodigoCliente(editText3.getText().toString());

        ServicesOlimpia.getInstance().getConsultProcess(consultarProceso, new OlimpiaInterface.CallbackConsultarProceso() {
            @Override
            public void onSuccess(ConsultarProcesoOut consultarProcesoOut) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(consultarProcesoOut));
            }

            @Override
            public void onError(RespuestaTransaccion respuestaTransaccion) {
                dismissProgressDialog();
                result.setText(JsonUtils.stringObject(respuestaTransaccion));
            }
        });
    }
}
