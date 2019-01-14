package com.bokun.bkjcb.carcheck.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.carcheck.Model.CheckItem;
import com.bokun.bkjcb.carcheck.Model.CheckResult;
import com.bokun.bkjcb.carcheck.MyApplication;
import com.bokun.bkjcb.carcheck.PhotoActivity;
import com.bokun.bkjcb.carcheck.R;
import com.bokun.bkjcb.carcheck.Utils.Bimp;
import com.bokun.bkjcb.carcheck.Utils.FileUtils;
import com.bokun.bkjcb.carcheck.Utils.LocalTools;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by DengShuai on 2018/11/3.
 * Description :
 */
public class CheckItemFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private GridView gridview;
    private HorizontalScrollView selectimg_horizontalScrollView;
    private float dp;

    public List<Bitmap> bmp = new ArrayList<>();
    public List<String> drr = new ArrayList<>();
    private CheckItem.CheckItemDetail item;
    private GridAdapter gridAdapter;
    private Long id;
    private CheckResult result;
    private EditText remark;
    private RadioButton btn_yes;
    private RadioButton btn_no;
    private RadioButton btn_none;
    private RadioGroup btn;

    @Override
    public View initView() {
        item = (CheckItem.CheckItemDetail) getArguments().get("item");
        id = (Long) getArguments().get("id");
        View view = View.inflate(getContext(), R.layout.check_item_fragment, null);
        TextView check_content = view.findViewById(R.id.check_content);
        remark = view.findViewById(R.id.comment_content);
        btn_yes = view.findViewById(R.id.btn_yes);
        btn_no = view.findViewById(R.id.btn_no);
        btn_none = view.findViewById(R.id.btn_none);
        btn = view.findViewById(R.id.btn);
        selectimg_horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.selectimg_horizontalScrollView);
        gridview = (GridView) view.findViewById(R.id.noScrollgridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        check_content.setText(item.getDescription());
        return view;
    }

    @Override
    public void initData() {
        dp = LocalTools.dip2px(getContext(), 10);
        gridAdapter = new GridAdapter(getContext());
        gridAdapter.setSelectedPosition(0);
        gridview.setAdapter(gridAdapter);
        gridview.setOnItemClickListener(this);
        gridview.setStretchMode(GridView.NO_STRETCH);
        result = MyApplication.getBoxStore().boxFor(CheckResult.class).get(id);
        initCheckResultData();
        gridViewInit();
    }

    private void initCheckResultData() {
        if (!TextUtils.isEmpty(result.getRemark())) {
            remark.setText(result.getRemark());
        }
        switch (result.getResult()) {
            case 1:
                btn_none.setChecked(true);
                break;
            case 2:
                btn_yes.setChecked(true);
                break;
            case 3:
                btn_no.setChecked(true);
                break;
            default:
        }
        if (!TextUtils.isEmpty(result.getImageUrl())) {
            drr.addAll(LocalTools.changeToList(result.getImageUrl()));
            for (int i = 0; i < drr.size(); i++) {
                Bitmap bitmap = Bimp.getLocalBitmap(drr.get(i));
                bitmap = Bimp.createFramedPhoto(480, 480, bitmap,
                        (int) (dp * 1.6f));
                bmp.add(bitmap);
            }
        }

    }

    public void gridViewInit() {

        int size = 0;
        if (bmp.size() < 6) {
            size = bmp.size() + 1;
        } else {
            size = bmp.size();
        }
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        final int width = size * (int) (dp * 9.4f);
        params.width = width;
        gridview.setLayoutParams(params);
        gridview.setColumnWidth((int) (dp * 9.4f));
        gridview.setNumColumns(size);
        selectimg_horizontalScrollView.getViewTreeObserver()
                .addOnPreDrawListener(// 绘制完毕
                        new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                selectimg_horizontalScrollView.scrollTo(width,
                                        0);
                                selectimg_horizontalScrollView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                return false;
                            }
                        });
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ((InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getActivity()
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        if (i == bmp.size()) {
            String sdcardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                new PopupWindows(getContext(), gridview);
            } else {
                Toast.makeText(getContext(), "sdcard已拔出，不能选择照片",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(getContext(),
                    PhotoActivity.class);

            intent.putExtra("ID", i);
            String[] urls = new String[drr.size()];
            drr.toArray(urls);
            intent.putExtra("url",urls);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (drr.size() < 6 && resultCode == -1) {// 拍照
                    startPhotoZoom(photoUri, false);
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (drr.size() < 6 && resultCode == RESULT_OK && null != data) {// 相册返回
                    Uri uri = data.getData();

                    if (uri != null) {
                        startPhotoZoom(uri, true);
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK && null != data) {// 裁剪返回
//                    CropUtil.handleCropResult(data, new File(drr.get(drr.size() - 1)));

                }
                break;
        }
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater listContainer;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public class ViewHolder {
            public ImageView image;
            public Button bt;
        }

        public GridAdapter(Context context) {
            listContainer = LayoutInflater.from(context);
        }

        public int getCount() {
            if (bmp.size() < 6) {
                return bmp.size() + 1;
            } else {
                return bmp.size();
            }
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int sign = position;
            // 自定义视图
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                // 获取list_item布局文件的视图

                convertView = listContainer.inflate(
                        R.layout.item_published_grida, null);

                // 获取控件对象
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                holder.bt = (Button) convertView
                        .findViewById(R.id.item_grida_bt);
                // 设置控件集到convertView
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                holder.bt.setVisibility(View.GONE);
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(bmp.get(position));
                holder.bt.setVisibility(View.VISIBLE);
                holder.bt.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                       // PhotoActivity.bitmap.remove(sign);
                        bmp.get(sign).recycle();
                        bmp.remove(sign);
                        FileUtils.delFile(drr.get(sign));
                        drr.remove(sign);
                        gridViewInit();
                    }
                });
            }

            return convertView;
        }
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(
                            // 相册
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    //CropUtil.pickFromGallery(getActivity(), RESULT_LOAD_IMAGE);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    private static final int SELECTIMG_SEARCH = 3;
    private String path = "";
    private Uri photoUri;

    public void photo() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            path = FileUtils.getSDPATH() + LocalTools.getDate(null) + ".JPEG";
            File file = new File(path);
            photoUri = getPhotoUri(openCameraIntent, file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri getPhotoUri(Intent intent, File file) {
        Uri uri = null;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            if (file != null) {
                /*获取当前系统的android版本号*/
                if (currentapiVersion < 24) {
                    uri = Uri.fromFile(file);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                    uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                }
            } else {
                Toast.makeText(getContext(), R.string.mis_error_image_not_exist, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.mis_msg_no_camera, Toast.LENGTH_SHORT).show();
        }
        return uri;
    }

    private void startPhotoZoom(Uri uri, boolean isCopy) {

        Bitmap bitmap = null;
        if (uri != null) {
            try {
                if (isCopy) {
                    // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹

                    String path = FileUtils.getSDPATH() + LocalTools.getDate(null) + ".JPEG";
                    File file = new File(path);
                    drr.add(path);
                    String url = LocalTools.getImagePath(getContext(), uri);
                    LocalTools.copyFile(file, url);
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                } else {
                    drr.add(path);
                    bitmap = Bimp.getLocalBitmap(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "照片有问题请重试！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //CropUtil.startCropActivity(uri, file, getActivity());

        bitmap = Bimp.createFramedPhoto(480, 480, bitmap,
                (int) (dp * 1.6f));
        bmp.add(bitmap);
        gridViewInit();
    }

    @Override
    public void onStop() {
        storeData();
        super.onStop();
    }

    private void storeData() {
        if (result != null) {
            result.setRemark(remark.getText().toString());
            result.setImageUrl(LocalTools.changeToString(drr));
            switch (btn.getCheckedRadioButtonId()) {
                case R.id.btn_yes:
                    result.setResult(2);
                    break;
                case R.id.btn_no:
                    result.setResult(3);
                    break;
                case R.id.btn_none:
                    result.setResult(1);
                    break;
                default:

            }
            MyApplication.getBoxStore().boxFor(CheckResult.class).put(result);
        }
    }
}
