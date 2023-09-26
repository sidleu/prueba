package com.reconosersdk;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.reconosersdk.databinding.ActivityServicesBinding;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.in.EnviarOTPAuthIDIn;
import com.reconosersdk.reconosersdk.http.enviarOTPAuthID.out.EnviarOTPAuthIDOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.CiudadanoIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ConsultarFuenteIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.DatosOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.EnviarOTPIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.GuardarLogErrorIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.RespondConsultarFuente;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.RespuestasIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.SearchForDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidarRespuestaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ani.ConsultarAniIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savelogs.LogMobileResult;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savelogs.LogSaveBarcode;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.savelogs.LogSaveOCR;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarCiudadano;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ConsultarConvenio;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.EnviarOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarCiudadano;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.GuardarLogError;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespondSearchDocument;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitarPreguntasDemograficas;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarOTP;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarRespuestaDemografica;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ani.ConsultarAniOut;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs.RespondLogIdentity;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs.RespondLogMobileResult;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String CONSULTAR_CONVENIO = "CONSULTAR CONVENIO";
    private static final String ENVIAR_OTP = "ENVIAR OTP";
    private static final String VALIDAR_OTP = "VALIDAR OTP";
    private static final String GUARDAR_CIUDADANO = "GUARDAR CIUDADANO";
    private static final String CONSULTAR_CIUDADANO = "CONSULTAR CIUDADANO";
    private static final String GUARDAR_BIOMETRIA = "GUARDAR BIOMETRIA";
    private static final String SOLICITAR_PREGUNTAS_DEMOGRAFICAS = "SOLICITAR PREGUNTAS DEMOGRAFICAS";
    private static final String VALIDARRES_PUESTA_DEMOGRAFICA = "VALIDARRES PUESTA DEMOGRAFICA";
    private static final String GUARDAR_LOG_ERROR = "GUARDAR LOG ERROR";
    private static final String BUSCAR_USUARIO = "BUSCAR CIUDADANO";
    private static final String FINALIZAR_PROCESO = "FINALIZAR PROCESO";
    private static final String CONSULTAR_FUENTE = "Consultar Fuente";
    private static final String GUARDAR_LOG_BARCODE = "Guardar Log Barcode";
    private static final String GUARDAR_LOG_OCR = "Guardar Log OCR";
    private static final String GUARDAR_LOG_RESULTADO_MOVIL = "Guardar Log Resultado Movil";
    private static final String CONSULTAR_ANI = "CONSULTAR ANI";
    private static final String ENVIAR_OTP_AUTH_ID = "ENVIAR OTP AuthID";



    private String selectedModel;
    private int service = 0;
    public ProgressDialog mProgressDialog;
    private ActivityServicesBinding binding;

    private String guidOTP = "5544f3bb-eb85-43fe-ab1e-f306b8a3f89d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Drawable yourIcon = getResources().getDrawable(R.drawable.back, null);
        getSupportActionBar().setHomeAsUpIndicator(yourIcon);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        ((TextView) v.findViewById(R.id.title)).setText(this.getTitle());
        getSupportActionBar().setCustomView(v);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_services);
        binding.result.setMovementMethod(new ScrollingMovementMethod());

        List<String> options = new ArrayList<>();
        options.add("CONSULTAR CONVENIO");
        options.add("ENVIAR OTP");
        options.add("VALIDAR OTP");
        options.add("GUARDAR CIUDADANO");
        options.add("CONSULTAR CIUDADANO");
        options.add("GUARDAR LOG ERROR");
        options.add("BUSCAR CIUDADANO");
        options.add("FINALIZAR PROCESO");
        options.add("Consultar Fuente");
        options.add("Guardar Log Barcode");
        options.add("Guardar Log OCR");
        options.add("Guardar Log Resultado Movil");
        options.add("CONSULTAR ANI");
        options.add("ENVIAR OTP AuthID");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_style);
        // attaching data adapter to spinner
        binding.spinner.setAdapter(dataAdapter);
        binding.spinner.setOnItemSelectedListener(this);
        binding.button7.setOnClickListener(v1 -> {
            loading("Guardando");
            selectorService(service);
        });
    }

    public void loading(String msgRes) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            //mProgressDialog.setMessage(getString(R.string.loading));
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

    private void selectorService(int service) {
        switch (service) {
            case 1:
                enviarOTP();
                break;
            case 2:
                validarOTP();
                break;
            case 3:
                guardarCiudadano();
                break;
            case 4:
                consultarCiudadano();
                break;
            case 5:
                guardarError();
                break;
            case 6:
                buscar();
                break;
            case 7:
                onFinishProcess();
                break;
            case 8:
                consultSource();
                break;
            case 9:
                setSaveLogBarcode();
                break;
            case 10:
                setSaveLogOCR();
                break;
            case 11:
                setSaveLogMobileResult();
                break;
            case 12:
                setConsultAni();
                break;
            case 13:
                enviarOTPAuthId();
                break;
            default:
                consultarConvenio();
                break;
        }
    }



    private void onFinishProcess() {
        //This state is always true when the process is successful finish
        boolean state = true;
        ServicesOlimpia.getInstance().onFinishProcess(binding.editText.getText().toString(), state, new OlimpiaInterface.CallbackFinishProcess() {
            @Override
            public void onSuccess(boolean esExitosa) {
                dismissProgressDialog();
                binding.result.setText(String.valueOf(esExitosa));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }

    private void buscar() {
        SearchForDocument idDocument = new SearchForDocument(binding.editText.getText().toString(), binding.editText2.getText().toString(), binding.editText3.getText().toString());
        ServicesOlimpia.getInstance().getSearchForDocument(idDocument, new OlimpiaInterface.CallbackSearchForDocument() {

            @Override
            public void onSuccess(RespondSearchDocument respondSearchDocument) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(respondSearchDocument));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }
   /* private void buscar() {
        SearchUser user = new SearchUser(binding.editText.getText().toString(), binding.editText2.getText().toString(), "57", "cc");
        ServicesOlimpia.getInstance().getClientFound(user, new OlimpiaInterface.CallbackSearchUser() {
            @Override
            public void onSuccess(UserFound userFound) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(userFound));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }*/

    private void guardarError() {

        GuardarLogErrorIn guardarLogErrorIn = new GuardarLogErrorIn("530a93cf-4fc3-45c7-855c-9a26306fa2a7", "Fallo", "Validar", "prueba8");

        ServicesOlimpia.getInstance().guardarlogError(guardarLogErrorIn, new OlimpiaInterface.CallbackSaveLogError() {
            @Override
            public void onSuccess(GuardarLogError saveLogError) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(saveLogError));
            }

            @Override
            public void onError(List<ErrorEntransaccion> transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }

    private void validarPreguntas() {
        ValidarRespuestaIn validarRespuestaIn = new ValidarRespuestaIn();
        validarRespuestaIn.setGuidCiudadano("414BB638-F052-4B67-B8D7-FCA55776865C");
        validarRespuestaIn.setIdCuestionario("Xcd54.drrsaTry");
        validarRespuestaIn.setRegistroCuestionario(1);
        List<RespuestasIn> respuestasIns = new ArrayList<>();
        respuestasIns.add(new RespuestasIn("005003002", "002"));
        respuestasIns.add(new RespuestasIn("005003003", "002"));
        respuestasIns.add(new RespuestasIn("005006002", "001"));
        respuestasIns.add(new RespuestasIn("005003008", "002"));
        validarRespuestaIn.setRespuestas(respuestasIns);

        ServicesOlimpia.getInstance().validarRespuestaDemograficas(validarRespuestaIn, new OlimpiaInterface.CallbackValidateResponse() {
            @Override
            public void onSuccess(ValidarRespuestaDemografica validateResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(validateResponse));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }

    private void requestPreguntas() {

        ServicesOlimpia.getInstance().solicitarPreguntasDemograficas(binding.editText.getText().toString(), new OlimpiaInterface.CallbackRequestQuestions() {
            @Override
            public void onSuccess(SolicitarPreguntasDemograficas requestQuestions) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(requestQuestions));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }

    private void consultarCiudadano() {

        //TODO consultarCiudadano
        ServicesOlimpia.getInstance().consultarCiudadano(binding.editText.getText().toString(), new OlimpiaInterface.CallbackConsultResident() {
            //ServicesOlimpia.getInstance().consultarCiudadano("4f49f210-20d4-4405-a6cb-9d1fc6461a5a", new OlimpiaInterface.CallbackConsultResident() {
            @Override
            public void onSuccess(ConsultarCiudadano consultResident) {
                dismissProgressDialog();
                binding.result.setText(consultResident.toString());
                binding.result.setText(JsonUtils.stringObject(consultResident));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                if (transactionResponse.getErrorEntransaccion() != null) {
                    binding.result.setText(transactionResponse.getErrorEntransaccion().toString());
                } else {
                    binding.result.setText(JsonUtils.stringObject(transactionResponse));
                }
            }
        });
    }

    private void showCiudadano() {
        binding.container.setVisibility(View.VISIBLE);
        visibildadText3(true);
        binding.textView5.setText("GuidConv");
        binding.editText.setText("1BF8FEA7-099D-4EA0-98A3-AC4A75206F27");
        binding.textView6.setText("GuidCiu");
        binding.editText2.setText("");
        binding.textView7.setText("TipoDoc");
        binding.editText3.setText("CC");
        binding.textView8.setText("NumDoc");
        binding.editText4.setText("");
        binding.textView9.setText("Email");
        binding.editText5.setText("");
        binding.textView10.setText("CodPais");
        binding.editText6.setText("57");
        binding.textView11.setText("Celular");
        binding.editText7.setText("");
        binding.editText8.setText("");
        binding.textView8.setVisibility(View.VISIBLE);
        binding.editText4.setVisibility(View.VISIBLE);
    }

    private void hideciudadano() {
        binding.container.setVisibility(View.INVISIBLE);
        visibildadText3(false);
    }

    private void guardarCiudadano() {

        //TODO guardarCiudadano

        CiudadanoIn ciudadano = new CiudadanoIn();
        ciudadano.setGuidConv(binding.editText.getText().toString());
        ciudadano.setGuidCiu(binding.editText2.getText().toString());
        ciudadano.setTipoDoc(binding.editText3.getText().toString());
        ciudadano.setNumDoc(binding.editText4.getText().toString());
        ciudadano.setEmail(binding.editText5.getText().toString());
        ciudadano.setCelular(binding.editText7.getText().toString());

        //TODO asignar setProcesoConvenioGuid
        ciudadano.setProcesoConvenioGuid("03B1404A-7FD2-411D-A110-0EA63621BF4C");
        ciudadano.setAsesor("testing");
        ciudadano.setSede("931135");

        ServicesOlimpia.getInstance().guardarCiudadano(ciudadano, new OlimpiaInterface.CallbackSaveResident() {
            @Override
            public void onSuccess(GuardarCiudadano saveResident) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(saveResident));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                if (transactionResponse.getErrorEntransaccion() != null) {
                    binding.result.setText(JsonUtils.stringObject(transactionResponse.getErrorEntransaccion()));
                } else {
                    binding.result.setText(JsonUtils.stringObject(transactionResponse));
                }
            }
        });
    }

    private void validarOTP() {
        //TODO validarOTP

        ServicesOlimpia.getInstance().validarOTP(binding.editText.getText().toString(), binding.editText2.getText().toString(), new OlimpiaInterface.CallbackValidateOTP() {
            @Override
            public void onSuccess(ValidarOTP validarOTP) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(validarOTP));
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                if (!transactionResponse.getErrorEntransaccion().isEmpty()) {
                    binding.result.setText(JsonUtils.stringObject(transactionResponse.getErrorEntransaccion()));
                } else {
                    binding.result.setText(JsonUtils.stringObject(transactionResponse));
                }
            }
        });
    }

    private void enviarOTP() {
        //TODO enviarOTP
        DatosOTP datosOTP = new DatosOTP(binding.editText2.getText().toString(), binding.editText3.getText().toString());
        EnviarOTPIn enviarOTPIn = new EnviarOTPIn();
        enviarOTPIn.setGuidCiudadano(binding.editText.getText().toString());
        enviarOTPIn.setGuidProcesoConvenio( binding.editText4.getText().toString());
        enviarOTPIn.setDatosOTP(datosOTP);

        ServicesOlimpia.getInstance().enviarOTP(enviarOTPIn, new OlimpiaInterface.CallbackSendOTP() {
            @Override
            public void onSuccess(EnviarOTP sendOTP) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(sendOTP));
                //set guidOTP
                guidOTP = sendOTP.getGuidOTP();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedModel = parent.getItemAtPosition(position).toString();
        createrServices(selectedModel);
    }

    private void visibildadText3(boolean status) {
        if (status) {
            binding.textView7.setVisibility(View.VISIBLE);
            binding.editText3.setVisibility(View.VISIBLE);
        } else {
            binding.textView7.setVisibility(View.GONE);
            binding.editText3.setVisibility(View.GONE);
        }

    }

    private void createrServices(String selectedModel) {
        switch (selectedModel) {
            case CONSULTAR_CONVENIO:
                service = 0;
                hideciudadano();
                showFields("GuidConv", "BFA3AB5F-BAEB-42DE-986E-0DA98F45F90F",
                        "Datos", "XX", null, null, null, null);
                visibildadText3(false);
                break;
            case ENVIAR_OTP:
                service = 1;
                showCiudadano();
                showFields("GuidCiudadano", "530a93cf-4fc3-45c7-855c-9a26306fa2a7",
                        "DatosOTP TipoOTP", "SMS", "Mensaje", "Es una prueba", "GuidProcesoConvenio", "319FE7B9-8282-43E1-9E5F-59569C3E3DA8");
                break;
            case VALIDAR_OTP:
                service = 2;
                hideciudadano();
                if(guidOTP == null || guidOTP.isEmpty()){
                    guidOTP = "5544f3bb-eb85-43fe-ab1e-f306b8a3f89d";
                }
                showFields("GuidOTP", guidOTP,
                        "OTP", "EKoY", null, null, null, null);
                break;
            case GUARDAR_CIUDADANO:
                service = 3;
                showCiudadano();
                break;
            case CONSULTAR_CIUDADANO:
                service = 4;
                hideciudadano();
                showFields("GuidCiudadano", "14D0D808-6A0F-49BA-B6A0-6E88AD5653AC",
                        "", "", null, null, null, null);
                visibildadText3(false);
                break;
            case GUARDAR_LOG_ERROR:
                service = 5;
                hideciudadano();
                showFields("GuidConv", "BFA3AB5F-BAEB-42DE-986E-0DA98F45F90F",
                        "Texto", "", "Componente", "", "Usuario", "");
                break;
            case BUSCAR_USUARIO:
                service = 6;
                hideciudadano();
                showFields("GuidConv", "97464498-D532-4964-ABF7-81DC0F884B21",
                        "Cedula", "", "tipo", "CC", null, null);
                break;
            case FINALIZAR_PROCESO:
                service = 7;
                hideciudadano();
                showFields("IdProceso", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        null, null, null, null, null, null);
                break;
            case CONSULTAR_FUENTE:
                service = 8;
                hideciudadano();
                showFields("IdProceso", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Cedula", "", "tipo", "CC", null, null);
                break;
            case GUARDAR_LOG_BARCODE:
                service = 9;
                hideciudadano();
                showFields("IdProceso", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Cedula", "", "tipo", "CC", null, null);
                break;
            case GUARDAR_LOG_OCR:
                service = 10;
                hideciudadano();
                showFields("IdProceso", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Cedula", "", "tipo", "CC", null, null);
                break;
            case GUARDAR_LOG_RESULTADO_MOVIL:
                service = 11;
                hideciudadano();
                showFields("IdProceso", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Cedula", "", "tipo", "CC", null, null);
                break;
            case CONSULTAR_ANI:
                service = 12;
                hideciudadano();
                showFields("IdProceso", "1BF8FEA7-099D-4EA0-98A3-AC4A75206F27",
                        "Cedula", "", "tipo", "CC", null, null);
                break;
            case ENVIAR_OTP_AUTH_ID:
                service = 13;
                showCiudadano();
                showFields("GuidConvenio", "97464498-D532-4964-ABF7-81DC0F884B21",
                        "DatosOTP TipoOTP", "SMS", "Mensaje", "Es una prueba", null, null);
                break;

        }
        binding.result.setText("");
    }

    private void showFields(String text1, String hint1,
                            String text2, String hint2,
                            String text3, String hint3,
                            String text4, String hint4) {

        if (text1 == null || text1.isEmpty()) {
            onHideFiel(binding.textView5, binding.editText);
        } else {
            onShowFielText(binding.textView5, binding.editText, text1, hint1);
        }
        if (text2 == null || text2.isEmpty()) {
            onHideFiel(binding.textView6, binding.editText2);
        } else {
            onShowFielText(binding.textView6, binding.editText2, text2, hint2);
        }
        if (text3 == null || text3.isEmpty()) {
            onHideFiel(binding.textView7, binding.editText3);
        } else {
            onShowFielText(binding.textView7, binding.editText3, text3, hint3);
        }
        if (text4 == null || text4.isEmpty()) {
            onHideFiel(binding.textView8, binding.editText4);
        } else {
            onShowFielText(binding.textView8, binding.editText4, text4, hint4);
        }
    }

    private void onHideFiel(TextView textView, EditText editText) {
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
    }

    private void onShowFielText(TextView textView, EditText editText, String text, String hint) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
        editText.setVisibility(View.VISIBLE);
        editText.setText(hint);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void consultarConvenio() {
        //TODO consultarConvenio
        ServicesOlimpia.getInstance().consultarConvenio(binding.editText.getText().toString(), binding.editText2.getText().toString(), new OlimpiaInterface.CallbackConsultAgreement() {

                    @Override
                    public void onSuccess(boolean estado, ConsultarConvenio serviciosConv) {
                        dismissProgressDialog();
                        binding.result.setText(JsonUtils.stringObject(serviciosConv));
                    }

                    @Override
                    public void onError(boolean estado, RespuestaTransaccion transactionResponse) {
                        dismissProgressDialog();
                        binding.result.setText(JsonUtils.stringObject(transactionResponse));

                    }
                }
        );
    }

    private void consultSource() {
        ConsultarFuenteIn consultarFuenteIn = new ConsultarFuenteIn();
        consultarFuenteIn.setCodigoPais("57");
        consultarFuenteIn.setTipoDocumento(binding.editText3.getText().toString());
        consultarFuenteIn.setNumDocumento(binding.editText2.getText().toString());
        consultarFuenteIn.setConvenioGuid(LibraryReconoSer.getGuidConv());
        ServicesOlimpia.getInstance().consultarFuente(consultarFuenteIn, new OlimpiaInterface.CallbackConsultSource() {

            @Override
            public void onSuccess(RespondConsultarFuente respondConsultarFuente) {
                binding.result.setText(JsonUtils.stringObject(respondConsultarFuente));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }

        });
    }

    private void setSaveLogBarcode() {
        LogSaveBarcode logSaveBarcode =  new Gson().fromJson("{\"ciudadanoGuid\":\"a120ceb5-ba2c-431c-ad22-1425257243f5\",\"fechaExpedicionDoc\":\"1963-08-09\",\"fechaNacimiento\":\"1963-08-09\",\"numeroDocumento\":\"79280375\",\"primerApellido\":\"MORENO\",\"primerNombre\":\"LUIS\",\"procesoConvenioGuid\":\"E7DBDC0D-5683-4E76-B0F9-0462FFF5F6D5\",\"rh\":\"O+\",\"segundoApellido\":\"LUGO\",\"segundoNombre\":\"FERNANDO\",\"sexo\":\"-\",\"tipoDocumento\":\"CC\"}", LogSaveBarcode.class);

        ServicesOlimpia.getInstance().guardarLogBarcode(logSaveBarcode, new OlimpiaInterface.CallbackLogSaveBarcode() {

            @Override
            public void onSuccess(RespondLogMobileResult respondLogMobileResult) {
                binding.result.setText(JsonUtils.stringObject(respondLogMobileResult));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }

        });
    }

    private void setSaveLogOCR() {
        LogSaveOCR logSaveOCR =  new Gson().fromJson("{\"ciudadanoGuid\":\"a120ceb5-ba2c-431c-ad22-1425257243f5\",\"fechaExpedicionDoc\":\"1981-10-15\",\"fechaNacimiento\":\"1963-08-09\",\"numeroDocumento\":\"9280375\",\"primerApellido\":\"-\",\"primerNombre\":\"-\",\"procesoConvenioGuid\":\"E7DBDC0D-5683-4E76-B0F9-0462FFF5F6D5\",\"rh\":\"-\",\"segundoApellido\":\"-\",\"segundoNombre\":\"-\",\"sexo\":\"-\",\"tipoDocumento\":\"CC\"}", LogSaveOCR.class);

        ServicesOlimpia.getInstance().guardarLogOCRDocumento(logSaveOCR, new OlimpiaInterface.CallbackLogSaveOCR() {

            @Override
            public void onSuccess(RespondLogMobileResult respondLogMobileResult) {
                binding.result.setText(JsonUtils.stringObject(respondLogMobileResult));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }

        });
    }

    private void setSaveLogMobileResult() {
        LogMobileResult logMobileResult =  new Gson().fromJson("{\"analisisDocumento\":99.27536231884058,\"analisisFacial\":18.14,\"encontradoBD\":false,\"objetoAnalisis\":\"{\\\"ponderacionTotal\\\":100.0,\\\"puntajeServicio\\\":[{\\\"descripcion\\\":\\\"Document\\\",\\\"detalle\\\":[{\\\"descipcion\\\":\\\"DOCUMENT\\\",\\\"ponderacion\\\":97.82608695652173,\\\"puntaje\\\":97.82608695652173},{\\\"descipcion\\\":\\\"BARCODE\\\",\\\"ponderacion\\\":100.0,\\\"puntaje\\\":100.0},{\\\"descipcion\\\":\\\"SDK\\\",\\\"ponderacion\\\":100.0,\\\"puntaje\\\":100.0}],\\\"ponderacion\\\":50.0,\\\"puntaje\\\":99.27536231884058},{\\\"descripcion\\\":\\\"Face\\\",\\\"detalle\\\":[{\\\"descipcion\\\":\\\"FACE_DOC\\\",\\\"ponderacion\\\":100.0,\\\"puntaje\\\":18.14}],\\\"ponderacion\\\":50.0,\\\"puntaje\\\":18.14}],\\\"puntajeTotal\\\":58.70768115942029}\",\"procesoConvenioGuid\":\"E7DBDC0D-5683-4E76-B0F9-0462FFF5F6D5\",\"puntajeTotal\":58.70768115942029}", LogMobileResult.class);

        ServicesOlimpia.getInstance().guardarLogResultadoMovil(logMobileResult, new OlimpiaInterface.CallbackLogMobileResult() {

            @Override
            public void onSuccess(RespondLogIdentity respondLogIdentity) {

                binding.result.setText(JsonUtils.stringObject(respondLogIdentity));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {

                binding.result.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }

        });
    }


    private void setConsultAni() {
        ConsultarAniIn consultarAniIn = new ConsultarAniIn();
        consultarAniIn.setTipoDocumento(binding.editText3.getText().toString());
        consultarAniIn.setNumeroDocumento(binding.editText2.getText().toString());
        consultarAniIn.setConvenioGuid(LibraryReconoSer.getGuidConv());
        ServicesOlimpia.getInstance().consultarAni(consultarAniIn, new OlimpiaInterface.CallbackConsultAni() {

            @Override
            public void onSuccess(ConsultarAniOut consultarAniOut) {
                binding.result.setText(JsonUtils.stringObject(consultarAniOut));
                dismissProgressDialog();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
                dismissProgressDialog();
            }

        });
    }

    private void enviarOTPAuthId() {
        //TODO enviarOTP
        DatosOTP datosOTP = new DatosOTP(binding.editText2.getText().toString(), binding.editText3.getText().toString());
        EnviarOTPAuthIDIn enviarOTPAuthIDIn = new EnviarOTPAuthIDIn();
        enviarOTPAuthIDIn.setGuidConvenio(binding.editText.getText().toString());
        enviarOTPAuthIDIn.setCorreo(binding.editText5.getText().toString());
        enviarOTPAuthIDIn.setNumeroTelefono(binding.editText7.getText().toString());
        enviarOTPAuthIDIn.setDatosOTP(datosOTP);
        ServicesOlimpia.getInstance().getSendOTPAuthID(enviarOTPAuthIDIn, new OlimpiaInterface.CallbackEnviarOTPAuthID() {
            @Override
            public void onSuccess(EnviarOTPAuthIDOut enviarOTPAuthIDOut) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(enviarOTPAuthIDOut));
                //set guidOTP
                guidOTP = enviarOTPAuthIDOut.getGuidOTP();
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                dismissProgressDialog();
                binding.result.setText(JsonUtils.stringObject(transactionResponse));
            }
        });
    }
}
