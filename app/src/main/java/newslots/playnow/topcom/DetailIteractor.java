package newslots.playnow.topcom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;


import io.reactivex.Single;

public class DetailIteractor implements InfoIterator {

    private HandlerAtg iDetailRepository;

    public DetailIteractor(HandlerAtg iDetailRepository) {
        this.iDetailRepository = iDetailRepository;
    }

    @Override
    public Single<Uri> getImg(ImageView posterImg, Context context) {
        Drawable drawable = posterImg.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable)
            bmp = ((BitmapDrawable) posterImg.getDrawable()).getBitmap();

        return iDetailRepository.getImg(bmp, context);
    }
}
