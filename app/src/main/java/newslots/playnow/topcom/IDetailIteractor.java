package newslots.playnow.topcom;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import io.reactivex.Single;

public interface IDetailIteractor {


    Single<Uri> getImg(ImageView posterImg, Context context);
}
