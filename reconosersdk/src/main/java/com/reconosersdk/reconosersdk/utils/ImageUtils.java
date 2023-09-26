package com.reconosersdk.reconosersdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ImageUtils {

    public static String convert64String(@NotNull String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
        bm.compress(Bitmap.CompressFormat.WEBP, 70, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /* IMAGE RESIZE */

    public static final Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static final Bitmap decodeSampledBitmapFromUri(Uri uri, ContentResolver contentResolver, int reqSize) throws FileNotFoundException {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqSize, reqSize);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
    }

    public boolean deleteImage(String mCurrentPhotoPath) {
        if (mCurrentPhotoPath == null) {
            return false;
        }

        File file = new File(mCurrentPhotoPath);
        boolean deleted = file.delete();
        return deleted;
    }

    /* BASE64 */

    public static String getEncodedBase64FromFilePath(String filePath) {
        //Avoid null or empties values
        if(filePath == null ||filePath.isEmpty() ){
            return "";
        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            String b64 = getEncodedBase64FromBitmap(bitmap);
            //Timber.e("Image type B64 is: %s", Miscellaneous.getTypeFile(filePath));
            return b64;
        }
    }

    //Founded in https://stackoverflow.com/questions/4830711/how-can-i-convert-an-image-into-a-base64-string
    public static String getEncodedBase64FromBitmap(Bitmap bitmap) {
        //Avoid null or empties values
        if(bitmap == null ){
            Timber.e("Can't encode B64 custom image: %s", "EMPTY bitmap");
            return "";
        }else {
            try{
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.MAX_QUALITY , byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String myB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                //Replace "\n" and "\"", sometimes the library doesn't encode correctly
                myB64 = myB64.replace("\n", "").replace("\"", "");
                //To know who is the mime type (as PNG, JPG, WEBP, GIF, etc)
                showMimeTypeB64(myB64);
                return myB64;
            }catch (Exception e){
                e.printStackTrace();
                Timber.e("Can't encode B64 custom image: %s", e.getMessage());
                return "";
            }
        }
    }

    //Founded in https://stackoverflow.com/questions/57976898/how-to-get-mime-type-from-base-64-string
    public static String showMimeTypeB64(String myB64) {
        try {
            List<String> mimeTypeList = new ArrayList<>();
            mimeTypeList.add("JVBERi0");
            mimeTypeList.add("R0lGODdh");
            mimeTypeList.add("R0lGODlh");
            mimeTypeList.add("iVBORw0KGgo");
            mimeTypeList.add("/9j/");
            mimeTypeList.add("U");
            mimeTypeList.add("J");

            List<String> fileTypeList = new ArrayList<>();
            fileTypeList.add("application/pdf");
            fileTypeList.add("image/gif");
            fileTypeList.add("image/gif");
            fileTypeList.add("image/png");
            fileTypeList.add("image/jpg");
            fileTypeList.add("image/webp");
            fileTypeList.add("application/pdf");

            //B64 mimeType
            String mimeType = "";

            boolean foundedType = false;
            for (int i = 0; i < mimeTypeList.size(); i++) {
                //Show the b64 mime type
                if (myB64.startsWith(mimeTypeList.get(i))) {
                    mimeType = fileTypeList.get(i);
                    Timber.e("Image B64 encoded type is: %s", mimeType);
                    foundedType = true;
                    break;
                }
            }
            if(!foundedType) {
                Timber.e("Image B64 encoded type is: %s", "not possible");
                return "not possible";
            }else{
                return mimeType;
            }
        }catch (Exception e){
            Timber.e("Image B64 encoded type is: %s", e.getMessage());
            return e.getMessage();
        }
    }

    public static Bitmap convert64toBitmap(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String getStringBase64(String path) { //TODO Funciona
        File imgFile = new File(path);
        if (imgFile.exists() && imgFile.length() > 0) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 85, bOut);
            String base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
            return base64Image;
        }
        return null;
    }

    public static Bitmap getSquareImage(Bitmap selectedImage) {
        if (selectedImage.getWidth() >= selectedImage.getHeight()) {
            selectedImage = Bitmap.createBitmap(selectedImage,
                    selectedImage.getWidth() / 2 - selectedImage.getHeight() / 2,
                    0,
                    selectedImage.getHeight(),
                    selectedImage.getHeight()
            );
        } else {
            selectedImage = Bitmap.createBitmap(selectedImage,
                    0,
                    selectedImage.getHeight() / 2 - selectedImage.getWidth() / 2,
                    selectedImage.getWidth(),
                    selectedImage.getWidth()
            );
        }
        return selectedImage;
    }

    public static String compressFileImageLow(Context context, String path) {
        File dir = new File(path);
        Bitmap b = BitmapFactory.decodeFile(path);
        Bitmap out = Bitmap.createScaledBitmap(b, Constants.WIDTH_LOW, Constants.HEIGHT_LOW, false);
        File file = new File(dir.getPath());
        try {
            FileOutputStream fOut;
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.WEBP, Constants.MAX_QUALITY, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            return file.getPath();
        } catch (Exception e) {
            Timber.e("Can't rescale Low image: %s", e.getMessage());
            return path;
        }
    }

    public static String compressImageHD(String path) {
        File dir = new File(path);
        Bitmap b = BitmapFactory.decodeFile(path);
        Bitmap out = Bitmap.createScaledBitmap(b, Constants.WIDTH_FACE, Constants.HEIGHT_FACE, false);
        File file = new File(dir.getPath());
        try {
            FileOutputStream fOut;
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.JPEG, Constants.MAX_QUALITY, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            return file.getPath();
        } catch (Exception e) {
            Timber.e("Can't rescale HD image: %s", e.getMessage());
            return path;
        }
    }

    public static String compressImageFullHD(Context context, String path) {
        File dir = new File(path);
        Bitmap b = BitmapFactory.decodeFile(path);
        Bitmap out = Bitmap.createScaledBitmap(b, Constants.WIDTH_FULL_HD, Constants.HEIGHT_FULL_HD, false);
        File file = new File(dir.getPath());
        try {
            FileOutputStream fOut;
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.WEBP, Constants.MAX_QUALITY, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            return file.getPath();
        } catch (Exception e) {
            Timber.e("Can't rescale full HD image: %s", e.getMessage());
            return path;
        }
    }

    public static String compressImageCustom(Context context, String path, Bitmap.CompressFormat formatImage, int width, int height ) {
        if(formatImage == null ){
            formatImage = Bitmap.CompressFormat.WEBP;
        }
        if(width <= 0){
            width =Constants.WIDTH_LOW;
        }
        if(height <= 0){
            height =Constants.HEIGHT_LOW;
        }

        File dir = new File(path);
        Bitmap b = BitmapFactory.decodeFile(path);
        Bitmap out = Bitmap.createScaledBitmap(b, width, height, false);
        File file = new File(dir.getPath());
        try {
            FileOutputStream fOut;
            fOut = new FileOutputStream(file);
            out.compress(formatImage, Constants.MAX_QUALITY, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            return file.getPath();
        } catch (Exception e) {
            Timber.e("Can't rescale custom image: %s", e.getMessage());
            return path;
        }
    }

    public static void rotationImage(@NonNull File tempphoto, Context mContext){
        String photopath = tempphoto.getPath();
        Bitmap bmp = BitmapFactory.decodeFile(photopath);

        Matrix matrix = new Matrix();
        switch (getImageRotation(Uri.fromFile(tempphoto), mContext)) {
            case 90:
                matrix.postRotate(90);
                break;
            case 180:
                matrix.postRotate(180);
                break;
            case 270:
                matrix.postRotate(270);
                break;
            default:
                matrix.postRotate(0);
        }
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(tempphoto);
            bmp.compress(Bitmap.CompressFormat.JPEG, 95, fOut);
            //bmp.compress(Bitmap.CompressFormat.WEBP, 95, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private static int getImageRotation(Uri imageUri, Context mContext) {
        try {

            ExifInterface exif = new ExifInterface(imageUri.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            if (rotation == ExifInterface.ORIENTATION_UNDEFINED)
                return getRotationFromMediaStore(imageUri, mContext);
            else return exifToDegrees(rotation);

        } catch (IOException e) {
            return 0;
        }
    }

    private static int getRotationFromMediaStore(Uri imageUri, Context mContext) {
        String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION };
        Cursor cursor = mContext.getContentResolver().query(imageUri, columns, null, null, null);

        if (cursor == null) return 0;

        cursor.moveToFirst();

        int orientationColumnIndex = cursor.getColumnIndex(columns[1]);
        int requestedColumn = cursor.getInt(orientationColumnIndex);

        cursor.close();

        return requestedColumn;
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        } else {
            return 0;
        }
    }

    public static File getResizeRescaledImage(int newHeight, int newWidth, int quality, File file){
        File newFile = getResizeImage(quality, file);
        newFile = Miscellaneous.getRescaledImage(newHeight, newWidth, quality, newFile.getAbsolutePath());
        return newFile;
    }

    //Founded in https://stackoverflow.com/questions/11688982/pick-image-from-sd-card-resize-the-image-and-save-it-back-to-sd-card
    public static File getRescaledImage(int newHeight, int newWidth, int quality, String path) {
        File dir = new File(path);
        Bitmap b = BitmapFactory.decodeFile(dir.getAbsolutePath());
        //Founded in https://stackoverflow.com/questions/8442316/bitmap-is-returning-null-from-bitmapfactory-decodefilefilename
        if(b == null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            b = BitmapFactory.decodeFile(dir.getAbsolutePath(), options);
        }
        Bitmap out = Bitmap.createScaledBitmap(b, newWidth, newHeight, false);
        if(quality <0 || quality > Constants.MAX_QUALITY){
            quality = Constants.MAX_QUALITY;
        }
        File file = new File(dir.getPath());
        try {
            FileOutputStream fOut;
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            return file;
        } catch (Exception e) {
            Timber.e("Can't rescale image: %s", e.getMessage());
            return new File(path);
        }
    }

    public static File getResizeImage(int quality, File file) {
        File dir = file;
        Bitmap b = BitmapFactory.decodeFile(dir.getAbsolutePath());
        //Founded in https://stackoverflow.com/questions/8442316/bitmap-is-returning-null-from-bitmapfactory-decodefilefilename
        BitmapFactory.Options options = new BitmapFactory.Options();
        if(b == null){
            options.inSampleSize = 2;
            b = BitmapFactory.decodeFile(dir.getAbsolutePath(), options);
        }else{
            options = Miscellaneous.getIMGSize(dir.getPath());
        }
        // Calcular la densidad de píxeles actual de la imagen
        int currentDensity = options.outHeight*options.outWidth;
        if(currentDensity>Constants.DENSITY_PIXEL_AVERAGE) {
            //Set the density pixel by average, the average is approximately 2,027,520
            double aspectRatio = (double) options.outWidth / options.outHeight;
            int newWidth = (int) Math.sqrt(Constants.DENSITY_PIXEL_AVERAGE * aspectRatio);
            double auxHeight = newWidth / aspectRatio;
            int newHeight = (int) Math.floor(auxHeight + 0.5d);
            Bitmap out = Bitmap.createScaledBitmap(b, newWidth, newHeight, false);
            if (quality < 0 || quality > Constants.MAX_QUALITY) {
                quality = Constants.MAX_QUALITY;
            }
            File newFile = new File(dir.getPath());
            try {
                FileOutputStream fOut;
                fOut = new FileOutputStream(newFile);
                out.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
                fOut.flush();
                fOut.close();
                b.recycle();
                out.recycle();
                return newFile;
            } catch (Exception e) {
                Timber.e("Can't rescale image: %s", e.getMessage());
                return file;
            }
        }else{
            Timber.e("Image rescale is unnecessary ===> Doesn't resize");
            return file;
        }
    }

    public static File isRadiusImage(int outHeight, int outWidth, File file) {
        try {
            double auxRadiusImage = (double) outHeight / outWidth;
            double auxLarge = 0.0;
            int newOutHeight = 1;
            int newOutWidth = 1;
            //The radius image is bigger than goal (1.33333 approximately)
            if (auxRadiusImage > Constants.MAXIMUM_RADIUS_IMAGE) {
                //Adjust the new width
                newOutWidth = (int) Math.round(outHeight*Constants.AVERAGE_RADIUS_IMAGE );
                newOutHeight = outHeight;
            } else if (auxRadiusImage < Constants.MINIMUM_RADIUS_IMAGE) {
                //Adjust the new height
                newOutHeight = (int) Math.round(outWidth*Constants.AVERAGE_RADIUS_IMAGE);
                newOutWidth = outWidth;
            } else {
                //the image doesn't change
                newOutHeight = outHeight;
                newOutWidth = outWidth;
            }
            auxRadiusImage = (double) newOutHeight / newOutWidth;
            String showText1 = "\n" + "Change path document is in: " + file.getAbsolutePath() + "\n" +
                    "image document height: " + newOutHeight + "\n" +
                    "image document width: " + newOutWidth + "\n" +
                    "image document radius: " + auxRadiusImage + "\n";
            Timber.e("Show compress change image is: %s", showText1);
            return Miscellaneous.getRescaledImage(newOutHeight, newOutWidth, Constants.MAX_QUALITY, file.getAbsolutePath());
        } catch (Exception e) {
            Timber.e("Error to compress image is: %s", e.getMessage());
            return file;
        }
    }
}
