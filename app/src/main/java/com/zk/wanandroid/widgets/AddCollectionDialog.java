package com.zk.wanandroid.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zk.wanandroid.R;
import com.zk.wanandroid.bean.Bookmark;
import com.zk.wanandroid.rxbus.RxBus;
import com.zk.wanandroid.utils.Constant;

/**
 * @description: 添加站外文章收藏PopupWindow
 * @author: zhukai
 * @date: 2018/3/14 15:36
 */
public class AddCollectionDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private int mType; // 对话框类型
    private LayoutInflater mInflater;
    private View mView;
    private TextView tvTitle;
    private LinearLayout llName;
    public EditText etName; // 网站名称
    private LinearLayout llTitle;
    public EditText etTitle; // 标题
    private LinearLayout llAuthor;
    public EditText etAuthor; // 作者
    private LinearLayout llLink;
    public EditText etLink; // 链接地址
    private TextView tvCancel; // 取消
    private TextView tvSubmit; // 确定

    public Bookmark mBookmark;

    public AddCollectionDialog(@NonNull Context context, int type) {
        super(context);
        this.mContext = context;
        this.mType = type;
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        mInflater = LayoutInflater.from(mContext);
        mView = mInflater.inflate(R.layout.layout_dialog_addcollection, null);
        this.setContentView(mView);

        tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        llName = (LinearLayout) mView.findViewById(R.id.ll_name);
        etName = (EditText) mView.findViewById(R.id.et_name);
        llTitle = (LinearLayout) mView.findViewById(R.id.ll_title);
        etTitle = (EditText) mView.findViewById(R.id.et_title);
        llAuthor = (LinearLayout) mView.findViewById(R.id.ll_author);
        etAuthor = (EditText) mView.findViewById(R.id.et_author);
        llLink = (LinearLayout) mView.findViewById(R.id.ll_link);
        etLink = (EditText) mView.findViewById(R.id.et_link);
        tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        tvSubmit = (TextView) mView.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(this);

        if (mType == Constant.DIALOG_ADD_COLLECTION) {
            // 添加收藏
            tvTitle.setText(mContext.getString(R.string.dialog_add_collection_title));
            llName.setVisibility(View.GONE);
        } else if (mType == Constant.DIALOG_ADD_BOOKMARK) {
            // 添加书签
            tvTitle.setText(mContext.getString(R.string.dialog_add_bookmark_title));
            llTitle.setVisibility(View.GONE);
            llAuthor.setVisibility(View.GONE);
        } else if (mType == Constant.DIALOG_EDIT_BOOKMARK) {
            // 编辑书签
            tvTitle.setText(mContext.getString(R.string.dialog_edit_bookmark_title));
            llTitle.setVisibility(View.GONE);
            llAuthor.setVisibility(View.GONE);
        }

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) mContext).getWindowManager();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 获取屏幕宽、高用
        Display display = manager.getDefaultDisplay();
        lp.gravity = Gravity.CENTER;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = (int) (display.getWidth() * 0.9);
        getWindow().setAttributes(lp);

        // 点击空白处和返回键不可取消
//        this.setCancelable(false);
        // 点击空白处不可取消
        this.setCanceledOnTouchOutside(false);
    }

    public Bookmark getBookmark() {
        return mBookmark;
    }

    /**
     * 设置书签内容
     *
     * @param bookmark
     */
    public void setBookmark(Bookmark bookmark) {
        this.mBookmark = bookmark;
        etName.setText(mBookmark.getName());
        etLink.setText(mBookmark.getLink());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                if (isShowing()) {
                    this.dismiss();
                }
                break;
            case R.id.tv_submit:
                if (mType == Constant.DIALOG_ADD_COLLECTION) {
                    RxBus.get().send(Constant.RX_BUS_CODE_ADD_COLLECTION);
                } else if (mType == Constant.DIALOG_ADD_BOOKMARK) {
                    RxBus.get().send(Constant.RX_BUS_CODE_ADD_BOOKMARK);
                } else if (mType == Constant.DIALOG_EDIT_BOOKMARK) {
                    mBookmark.setName(etName.getText().toString().trim());
                    mBookmark.setLink(etLink.getText().toString().trim());
                    RxBus.get().send(Constant.RX_BUS_CODE_EDIT_BOOKMARK);
                }
                break;
            default:
                break;
        }
    }
}
