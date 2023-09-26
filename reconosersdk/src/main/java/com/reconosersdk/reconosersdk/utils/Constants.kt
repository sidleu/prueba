package com.reconosersdk.reconosersdk.utils

class Constants {

    companion object {
        const val ANVERSO = "Anverso"
        const val REVERSO = "Reverso"
        const val FILE_NAME = "fileName"
        const val BIOMETRIA = "biometria"
        const val TAKE_PHOTO_DOCUMENT = 1020
        const val CLOUD_DOCUMENT = 1021
        const val VIEW_QUESTIONS = 1022
        const val ONE_HUNDRED = 100
        const val SOLO_ROSTRO_VIVO = "SoloRostroVivo"
        const val JPG_64 = "JPG_64"

        const val NOMBRES = "NOMBRES"

        const val IMAGE64 = "imageBase64"
        const val SOURCE_IMAGE = "SourceImage"
        const val TARGE_IMAGE = "TargetImage"

        const val LOREM_IPSUM = "Lorem ipsum dolor sit amet"

        //Colombian obverse document
        const val COLOMBIAN_NATIONALITY = "REPUBLICA DE COLOMBIA"
        const val COLOMBIAN_REPUBLIC= "REPUBLICA"
        const val COLOMBIAN_COUNTRY= "COLOMBIA"
        const val COLOMBIAN_CC = "CEDULA DE CIUDADANIA"
        //Only to search the names and last names
        const val COLOMBIAN_LAST_NAMES = "APELLIDO"
        const val COLOMBIAN_LAST_NAMES_AUX = "NUMERO"
        const val COLOMBIAN_LAST_NAMES_AUX1 = "LOMB"
        const val COLOMBIAN_NAMES = "APELLIDO"
        const val COLOMBIAN_NAMES_OK = "NOMBRES"
        const val COLOMBIAN_NAMES_AUX1 = "NDMAES"
        const val COLOMBIAN_NAMES_AUX2 = "oES"
        const val COLOMBIAN_NAMES_AUX3 = "NOMèRES"
        const val COLOMBIAN_NAMES_AUX4 = "nRDTBLIC"


        //const val COLOMBIAN_CC = "TARJETA DE IDENTIDAD"
        const val APELLIDOS = "APELLIDOS"
        const val NUMBER = "NUMERO"

        //Colombian reverse document
        const val COLOMBIAN_BORN = "NACIMIENTO"
        const val COLOMBIAN_BORN_DATE = "FECHA DE NACIMIENTO"
        const val COLOMBIAN_BORN_PLACE = "LUGAR DE NACIMIENTO"
        const val COLOMBIAN_EXPEDITION_PLACE = "FECHA Y LUGAR"
        const val COLOMBIAN_GENDER = "SEXO"
        const val COLOMBIAN_HEIGHT = "ESTATURA"
        const val COLOMBIAN_CC_NOT_DATE = "1901-01-01"

        //Colombian minor obverse document
        const val COLOMBIAN_TI_NATIONALITY = "REPUBLICA DE COLOMBIA"
        const val COLOMBIAN_TI = "TARJETA DE IDENTIDAD"
        const val APELLIDOS_TI = "APELLIDOS"
        const val NUMBER_TI = "NUMERO"

        //Colombian minor reverse document
        const val COLOMBIAN_TI_BORN = "NACIMIENTO"
        const val COLOMBIAN_TI_BORN_DATE = "FECHA DE NACIMIENTO"
        const val COLOMBIAN_TI_BORN_PLACE = "LUGAR DE NACIMIENTO"
        const val COLOMBIAN_TI_EXPIRATION = "VENCIMIENTO"
        const val COLOMBIAN_TI_EXPIRATION_DATE = "FECHA DE VENCIMIENTO"
        const val COLOMBIAN_TI_GENDER = "SEXO"
        const val EXPIRATION = "VENCIMIENTO"

        //Ecuadorian obverse document
        const val ECUADORIAN_NATIONALITY = "REPUBLICA DEL ECUADOR"
        const val ECUADORIAN_ID_FACILITY = "DIRECCION GENERAL DE REGISTRO CIVIL"
        const val ECUADORIAN_CC = "IDENTIFICACION Y CEDULACION"
        const val ECUADORIAN_NAMES = "APELLIDOS Y NOMBRES"
        const val ECUADORIAN_GENDER = "SEXO"

        const val ECUADORIAN_BORN_PLACE = "LUGAR DE NACIMIENTO"

        //Ecuadorian reverse document
        const val ECUADORIAN_INSTRUCTION = "INSTRUCCION"
        const val ECUADORIAN_EXPEDITION_DATE = "LUGAR Y FECHA DE EXPEDICION"
        const val ECUADORIAN_EXPIRATION_DATE = "FECHA DE EXPIRACION"

        //Foreigner obverse document
        const val FOREIGNER_NATIONALITY = "REPUBLICA DE COLOMBIA"
        const val FOREIGNER_CC = "CEDULA DE EXTRANJERIA"
        const val FOREIGNER_USER_NATIONALITY = "NACIONALIDAD"
        const val FOREIGNER_USER_VENCE = "VENCE"
        const val FOREIGNER_USER_EXP = "EXPEDICION"
        const val FOREIGNER_USER_DATE = "FECHA DE NACIMIENTO"
        const val FOREIGNER_LASTNAME = "APELLIDOS"

        //Foreigner reverse document
        const val FOREIGNER_MIGRATION = "WWW.MIGRACIONCOLOMBIA.GOV.CO"
        const val FOREIGNER_DIRECTOR_MIGRATION = "DIRECTOR MIGRACIÓN COLOMBIA"
        const val FOREIGNER_MIGRATION_2 = "EN LA CONDICION O INFORMACION MIGRATORIA"

        //Mexican federal obverse document
        const val MEXICAN_TITLE = "INSTITUTO FEDERAL ELECTORAL"
        const val MEXICAN_SUBTITLE = "REGISTRO FEDERAL DE ELECTORES"
        const val MEXICAN_CREDENTIAL = "CREDENCIAL PARA VOTAR"
        const val MEXICAN_NAMES = "NOMBRE"
        const val MEXICAN_HOME = "DOMICILIO"
        const val MEXICAN_INVOICE = "FOLIO"
        const val MEXICAN_INVOICE_2 = "FOUO"
        const val MEXICAN_KEY = "CLAVE DE ELECTOR"
        const val MEXICAN_CURP = "CURP"
        const val MEXICAN_STATE = "ESTADO"
        const val MEXICAN_LOCATION = "LOCALIDAD"
        const val MEXICAN_ISSUE = "EMISIÓN"
        const val MEXICAN_MUNICIPALITY = "MUNICIPIO"
        const val MEXICAN_SECTION = "SECCION"
        const val MEXICAN_VALIDITY = "VIGENCIA HASTA"
        const val MEXICAN_AGE = "EDAD"
        const val MEXICAN_SEX = "SEXO"
        const val MEXICAN_YEAR_REGISTRATION = "AÑO DE REGISTRO"
        const val MEXICAN_FIRM = "FIRMA"

        //Mexican federal reverse document
        const val MEXICAN_REVERSE = "ESTE DOCUMENTO ES INTRANSFERIBLE"
        const val MEXICAN_REVERSE_2 = "SECRETARIO EJECUTIVO"

        //Mexican national obverse
        const val MEXICAN_NAT_TITTLE = "INSTITUTO NACIONAL ELECTORAL"
        const val MEXICAN_COUNTRY = "MEXICO CREDENCIAL PARA VOTAR"

        //Mexican national reverse
        const val FEDERAL_ELECTION = "INE"
        const val FEDERAL_ELECTION_NUM = "1NE"
        const val FEDERAL_ELECTION_IFE_NUM = "1FE"
        const val FEDERAL_ELECTION_IFE = "IFE"
        const val MEX = "IDMEX"

        //Digital Colombian obverse document
        const val COLOMBIAN_DIGITAL_NATIONALITY = "REPUBLICA DE COLOMBIA"
        const val COLOMBIAN_DIGITAL_NUIP = "NUIP"
        const val COLOMBIAN_DIGITAL_ANOTHER_NUIP = "NU1P"
        const val COLOMBIAN_DIGITAL_DATE_EXPEDITION = "FECHA Y LUGAR DE EXPEDICION"
        const val COLOMBIAN_DIGITAL_GS = "G.S."
        const val COLOMBIAN_DIGITAL_GS1 = "GS."
        const val COLOMBIAN_DIGITAL_GS2 = "G.S"
        const val COLOMBIAN_DIGITAL_GS3 = "GS"
        const val COLOMBIAN_DIGITAL_GS4 = "9.S."
        const val COLOMBIAN_DIGITAL_DATE_EXPIRATION = "FECHA DE EXPIRACION"
        const val COLOMBIAN_DIGITAL_LAST_NAMES = "APELLIDOS"
        const val COLOMBIAN_DIGITAL_NAMES = "NOMBRES"
        const val COLOMBIAN_DIGITAL_ANOTHER_NAMES = "Nombres"
        const val COLOMBIAN_DIGITAL_ANOTHER_CEDULA = "CEDULA"
        const val COLOMBIAN_DIGITAL_ANOTHER_CIUDADANIA = "CIUDADANIA"
        //Digital Colombian obverse Reverse
        const val COLOMBIAN_DIGITAL_ALMOST_CO = ".C"
        const val COLOMBIAN_DIGITAL_CO = ".CO"
        const val COLOMBIAN_DIGITAL_REGISTER = "REGISTRADOR NACIONAL"
        const val COLOMBIAN_DIGITAL_ICCOL = "ICCOL"
        const val COLOMBIAN_DIGITAL_ICC0L = "ICC0L"
        const val COLOMBIAN_DIGITAL_FAKE_COL = "C01"
        const val COLOMBIAN_DIGITAL_FAKE_COL1 = "C001"
        const val COLOMBIAN_DIGITAL_FAKE_COL2 = "C1"
        const val COLOMBIAN_DIGITAL_TYPE = "Colombian CCD"
        const val COLOMBIAN_DIGITAL_GENDER_404 = "N/A"
        const val COLOMBIAN_DIGITAL_NOT_DATE = "01-ENE-01"
        const val COLOMBIAN_DIGITAL_SEX = "SEX"
        const val COLOMBIAN_DIGITAL_ANOTHER_SEX = "S3X"
        const val COLOMBIAN_DIGITAL_ANOTHER_SEX1 = "Soxo"
        const val COLOMBIAN_DIGITAL_COUNTRY_CITIZEN = "NACIONALIDAD"
        const val COLOMBIAN_DIGITAL_HEIGHT = "ESTATURA"
        const val COLOMBIAN_DIGITAL_ANOTHER_HEIGHT = "ESTATURA"
        const val COLOMBIAN_DIGITAL_ANOTHER_LAST_NAMES = "APEOOS"


        //kind of Colombian PDF_417
        const val PDF_417 = "PDF_417"
        const val PDF_417_CC = "PDF_417/CC"
        const val PDF_417_TI = "PDF_417/TI"
        const val PDF_417_CE = "PDF_417/CE"

        //Kind of document
        const val COLOMBIAN_OBVERSE_DOCUMENT = 1
        const val COLOMBIAN_REVERSE_DOCUMENT = 2
        const val COLOMBIAN_TI_OBVERSE_DOCUMENT = 3
        const val COLOMBIAN_TI_REVERSE_DOCUMENT = 4
        const val ECUADORIAN_OBVERSE_DOCUMENT = 5
        const val ECUADORIAN_REVERSE_DOCUMENT = 6
        const val FOREIGNER_OBVERSE_DOCUMENT = 7
        const val FOREIGNER_REVERSE_DOCUMENT = 8
        const val MEXICAN_OBVERSE_DOCUMENT = 9
        const val MEXICAN_REVERSE_DOCUMENT = 10
        const val COLOMBIAN_DIGITAL_OBVERSE_DOCUMENT = 11
        const val COLOMBIAN_DIGITAL_REVERSE_DOCUMENT = 12

        const val ONE_SECOND = 1000
        const val ONE_MINUTE = 60000

        const val DELAY_CAMERA_SCREEN = 5000
        const val FOUNDED_TEXT_TIME_OUT = 20000
        const val CAMERA_TIME_OUT = 20000

        const val FACE_CAMERA_TIME_OUT = 30000
        const val FACE_CAMERA_COUNT_DOWN_TIME = 15000
        const val FACE_DELAY_CAMERA_SCREEN = 5000
        const val FACE_DELAY_CHANGE_CHALLENGE =1350

        const val FIRST_MALE = 19999999
        const val FIRST_FEMALE = 69999999
        const val SECOND_MALE = 99999999

        const val DOUBLE_WORD_TOLERANCE = 0.7
        const val ARRAY_BOOLEAN = 4
        const val MINIMUM_LENGTH_ECUADORIAN_DOCUMENT = 9


        const val CHILE = "Chile"
        const val COLOMBIA = "Colombia"
        const val COSTA_RICAN = "Costa Rica"
        const val ECUADOR = "Ecuador"
        const val EL_SALVADOR = "El Salvador"
        const val GUATEMALA =  "Guatemala"
        const val HONDURAS = "Honduras"
        const val MEXICO = "México"
        const val PANAMA = "Panamá"
        const val PERU = "Perú"

        //to set image face resolution
        const val WIDTH_FACE = 860
        const val HEIGHT_FACE = 1200
        const val MAX_QUALITY = 100

        //to set image face resolution
        const val WIDTH_DOCUMENT = 860
        const val HEIGHT_DOCUMENT = 1200



        const val WIDTH_LOW = 640
        const val HEIGHT_LOW = 480

        const val WIDTH_HD = 1280
        const val HEIGHT_HD = 720

        const val WIDTH_FULL_HD = 1920
        const val HEIGHT_FULL_HD = 1080

        const val MINIMUM_RADIUS_IMAGE = 1.3
        const val AVERAGE_RADIUS_IMAGE = 1.333333333
        const val MAXIMUM_RADIUS_IMAGE = 1.4
        const val DENSITY_PIXEL_AVERAGE = 2027520
        const val SCREEN_RADIUS = 1.7
        const val IMAGE_QUALITY = 70


        const val FACE_NOT_FOUND = 0
        const val FACE_FOUND = 1
        const val FACE_FAR = 2
        const val FACE_NEAR = 3





        //To force mobileRead
        const val FORCE_DOCUMENT_READING = "ForzarLecturaMovil"

        const val ERROR_HOST = "No se pudo hacer conexión con el servidor"
        const val ERROR_R100 = "R_100"

        const val ERROR_CONV = "No esta activo el convenio"
        const val ERROR_R101 = "R_101"

        const val ERROR_SERVICE_API = "El convenio no tiene habilitado este servicio"
        const val ERROR_R102 = "R_102"

        const val ERROR_SERVER = "An error has occurred."
        const val ERROR_R103 = "R_103"

        const val ERROR_IMAGE_LOAD = "Error al cargar la imagen"
        const val ERROR_R104 = "R_104"

        const val ERROR_IMAGE_SAVE = "Error al guardar la imagen"
        const val ERROR_R105 = "R_105"

        const val ERROR_IMAGE_PROCESS = "No se pudo procesar la imagen"
        const val ERROR_R106 = "R_106"

        const val ERROR_NO_INIT = "No ha inicializado el convenio"
        const val ERROR_R99 = "R_99"

        const val ERROR_NO_PARAM = "No se encuentra el parametro"
        const val ERROR_R107 = "R_107"

        const val ERROR_NO_VALID = "Datos de Entrada no es válido"
        const val ERROR_102 = "102"

        const val ERROR_NO_PARAM_GUICIU = "No se encuentra el parametro guidCiudadano"
        const val ERROR_R108 = "R_108"

        const val ERROR_NO_PARAM_TYPE = "No se encuentra el parametro type document"
        const val ERROR_R109 = "R_109"

        const val ERROR_NO_PARAM_NUM = "No se encuentra el parametro número documento"
        const val ERROR_R110 = "R_110"

        const val ERROR_PARAM_NUM = "No cumple el minimo o maximo de longitud"
        const val ERROR_R111 = "R_111"

        const val ERROR_VALIDATE_BIOMETRY = "No se pudo realizar la validación biometrica"
        const val ERROR_R112 = "R_112"

        const val ERROR_NO_PARAM_SAVE_USER = "No se encuentra el parametro saveUser"
        const val ERROR_R113 = "R_113"

        const val ERROR_TIMEOUT = "El tiempo de espera de detección biométrica falló"
        const val ERROR_R116 = "R_116"

        const val ERROR_NO_FOUND_PARAM = "No se encuentra el parametro"
        const val ERROR_R404 = "R_404"

        const val ERROR_DOCUMENT_NOT_SIMILITUD = "No hay similitud en la información procesada"
        const val ERROR_R114 = "R_114"

        const val ERROR_DOCUMENT_NOT_BARCODE = "No se pudo leer el barcode, mejora las condiciones de iluminación"
        const val ERROR_R115 = "R_115"

        const val ERROR_NO_FOUND = "Proceso convenio no encontrado"
        const val ERROR_R117 = "R_117"

        const val ERROR_REQUEST_MEX = "El documento no supero los filtros de seguridad."
        const val ERROR_R118 = "R_118"
        const val ERROR_NOT_READ_BARCODE = "No fue posible leer la información del documento (Barcode)"
        const val ERROR_R119 = "R_119"
        const val ERROR_NOT_READ_DATE = "No fue posible leer las fechas en el documento."
        const val ERROR_R120 = "R_120"
        const val ERROR_DOCUMENT_VALIDATION = "Error de validación tipo de documento no válido."
        const val ERROR_R121 = "R_121"
    }
}