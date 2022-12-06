package com.blankspace.sakura.common;

/**
 * 默认的图片九宫格
 */
//public class ImageViewNineGridLayout extends AbstractNineGridLayout<List<ImageInfo>> {
//
//    private ImageView[] imageViews;
//    private final ImageLoader mImageLoader;
//
//    public ImageViewNineGridLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mImageLoader = new NineGlideLoader();
//    }
//
//    @Override
//    protected void fillChildView() {
//        inflateChildLayout(R.layout.item_image_grid);
//
//        imageViews = findInChildren(R.id.iv_image, ImageView.class);
//    }
//
//    @Override
//    public void renderData(List<ImageInfo> imageInfos) {
//
//        setSingleModeSize(imageInfos.get(0).getImageViewWidth(), imageInfos.get(0).getImageViewHeight());
//
//        setDisplayCount(imageInfos.size());
//
//        for (int i = 0; i < imageInfos.size(); i++) {
//            String url = imageInfos.get(i).getThumbnailUrl();
//
//            ImageView imageView = imageViews[i];
//
//            //使用自定义的Loader加载
//            mImageLoader.onDisplayImage(getContext(), imageView, url);
//
//            //点击事件
//            setClickListener(imageView, i, imageInfos);
//        }
//    }
//
//    //设置内部每一个图片的点击事件,跳转到预览页面
//    private void setClickListener(ImageView imageView, int position, List<ImageInfo> imageInfos) {
//
//        imageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                List<Object> list = new ArrayList<>();
//                for (ImageInfo imageInfo : imageInfos) {
//                    if (imageInfo != null) {
//                        list.add(imageInfo.getThumbnailUrl());
//                    }
//                }
//
//                if (mListener != null) {
//                    mListener.onPreview(imageViews, position, list);
//                }
//
//            }
//        });
//    }
//
//    private OnPreViewListener mListener;
//
//    public void setOnPreViewListener(OnPreViewListener listener) {
//        mListener = listener;
//    }
//
//    public interface OnPreViewListener {
//        void onPreview(ImageView[] imageViews, int position, List<Object> imageInfos);
//    }
//
//}