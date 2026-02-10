package com.app;
import android.app.Activity;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.MotionEvent;
import android.view.Gravity;
import android.graphics.Color;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Point;
//888
import android.content.Intent;
import android.net.Uri;



public class showLoginUI
{
    //dp转px的辅助方法
    private static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

	
	
	
	
	public static Activity ckActivity = null;
	
	public static void ts(String str)
	{
		Toast.makeText(ckActivity, str, Toast.LENGTH_SHORT).show();
	};

	
	
	
    public static void showLoginUI(Activity activity)
	{
		ckActivity = activity;
		
        final Activity context2 = activity; 
		 

        // 创建FrameLayout作为根布局
        final FrameLayout rootLayout = new FrameLayout(activity);
        rootLayout.setLayoutParams(new FrameLayout.LayoutParams(
									   FrameLayout.LayoutParams.MATCH_PARENT,
									   FrameLayout.LayoutParams.MATCH_PARENT));

        // 创建FrameLayout作为分组框
        final FrameLayout groupBox = new FrameLayout(activity);
        final FrameLayout.LayoutParams groupBoxParams = new FrameLayout.LayoutParams(
            dpToPx(activity, 150), // 宽度150dp
            dpToPx(activity, 250)  // 高度250dp
        );

        // 初始位置
        final int screenWidth = context2.getResources().getDisplayMetrics().widthPixels;
        final int screenHeight = context2.getResources().getDisplayMetrics().heightPixels;
        groupBoxParams.leftMargin = (screenWidth - dpToPx(activity, 150)) / 2;
        groupBoxParams.topMargin = (screenHeight - dpToPx(activity, 250)) / 2;

        groupBox.setLayoutParams(groupBoxParams);
        groupBox.setBackgroundColor(Color.parseColor("#ADD8E6")); // 水蓝色背景

        // 添加左上角收缩/展开按钮
        final Button collapseButton = new Button(activity);
        collapseButton.setText("-");
        collapseButton.setTextSize(14);
        collapseButton.setAllCaps(false);
        collapseButton.setBackgroundColor(Color.parseColor("#87CEEB")); // 稍深的水蓝色

        FrameLayout.LayoutParams collapseBtnParams = new FrameLayout.LayoutParams(
            dpToPx(activity, 20),
            dpToPx(activity, 20)
        );
        collapseBtnParams.leftMargin = 5;
        collapseBtnParams.topMargin = 5;
        collapseButton.setLayoutParams(collapseBtnParams);

        // 添加"主菜单"文本
        final TextView menuTitle = new TextView(activity);
        menuTitle.setText("主菜单");
        menuTitle.setTextSize(16);
        menuTitle.setTextColor(Color.BLACK);
        menuTitle.setGravity(Gravity.CENTER_VERTICAL);

        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.leftMargin = dpToPx(activity, 50); // 在按钮右边
        titleParams.topMargin = dpToPx(activity, 3);   // 和按钮顶部对齐
        menuTitle.setLayoutParams(titleParams);

        // 设置收缩/展开按钮点击事件
        final boolean[] isCollapsed = {false}; // 收缩状态
        final int originalWidth = dpToPx(activity, 150);
        final int originalHeight = dpToPx(activity, 250);
        final int collapsedSize = dpToPx(activity, 30); // 收缩为30dp圆形

        collapseButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isCollapsed[0]) {
						// 收缩为圆形
						groupBoxParams.width = collapsedSize;
						groupBoxParams.height = collapsedSize;

						// 创建圆形背景
						GradientDrawable circleDrawable = new GradientDrawable();
						circleDrawable.setShape(GradientDrawable.OVAL);
						circleDrawable.setColor(Color.parseColor("#ADD8E6"));

						groupBox.setBackground(circleDrawable);
						groupBox.setLayoutParams(groupBoxParams);

						// 隐藏所有子视图（除了收缩按钮）
						for (int i = 0; i < groupBox.getChildCount(); i++) {
							View child = groupBox.getChildAt(i);
							if (child != collapseButton) {
								child.setVisibility(View.GONE);
							}
						}

						// 更新收缩按钮位置和文本
						FrameLayout.LayoutParams btnParams = (FrameLayout.LayoutParams) collapseButton.getLayoutParams();
						btnParams.gravity = Gravity.CENTER;
						btnParams.leftMargin = 0;
						btnParams.topMargin = 0;
						collapseButton.setLayoutParams(btnParams);
						collapseButton.setText("+");

						isCollapsed[0] = true;
					} else {
						// 展开为原始大小
						groupBoxParams.width = originalWidth;
						groupBoxParams.height = originalHeight;
						groupBox.setBackgroundColor(Color.parseColor("#ADD8E6"));
						groupBox.setLayoutParams(groupBoxParams);

						// 显示所有子视图
						for (int i = 0; i < groupBox.getChildCount(); i++) {
							View child = groupBox.getChildAt(i);
							child.setVisibility(View.VISIBLE);
						}

						// 恢复收缩按钮位置和文本
						FrameLayout.LayoutParams btnParams = (FrameLayout.LayoutParams) collapseButton.getLayoutParams();
						btnParams.gravity = Gravity.NO_GRAVITY;
						btnParams.leftMargin = 5;
						btnParams.topMargin = 5;
						collapseButton.setLayoutParams(btnParams);
						collapseButton.setText("-");

						isCollapsed[0] = false;
					}
				}
			});

        // 为分组框添加拖动功能
        groupBox.setOnTouchListener(new View.OnTouchListener() {
				private int initialX, initialY;
				private float initialTouchX, initialTouchY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// 如果是收缩状态，整个圆形都可以拖动
					if (isCollapsed[0]) {
						return handleDrag(v, event);
					} else {
						// 展开状态，检查是否点击了标题栏区域
						float x = event.getX();
						float y = event.getY();

						// 定义标题栏区域（高度为30dp）
						if (y > dpToPx(context2, 30)) {
							// 如果不是点击标题栏区域，则执行拖动
							return handleDrag(v, event);
						}
					}
					return false;
				}

				private boolean handleDrag(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							initialX = groupBoxParams.leftMargin;
							initialY = groupBoxParams.topMargin;
							initialTouchX = event.getRawX();
							initialTouchY = event.getRawY();
							return true;

						case MotionEvent.ACTION_MOVE:
							float deltaX = event.getRawX() - initialTouchX;
							float deltaY = event.getRawY() - initialTouchY;

							int newX = (int) (initialX + deltaX);
							int newY = (int) (initialY + deltaY);

							// 边界限制
							int currentWidth = isCollapsed[0] ? collapsedSize : originalWidth;
							int currentHeight = isCollapsed[0] ? collapsedSize : originalHeight;
							int maxX = screenWidth - currentWidth;
							int maxY = screenHeight - currentHeight;

							if (newX < 0) newX = 0;
							if (newX > maxX) newX = maxX;
							if (newY < 0) newY = 0;
							if (newY > maxY) newY = maxY;

							groupBoxParams.leftMargin = newX;
							groupBoxParams.topMargin = newY;
							groupBox.setLayoutParams(groupBoxParams);
							return true;

						case MotionEvent.ACTION_UP:
							return true;
					}
					return false;
				}
			});

        // 创建登录按钮
        Button loginButton = new Button(activity);
        loginButton.setText("登录按钮");

        FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(
            dpToPx(activity, 120),
            dpToPx(activity, 50)
        );
        buttonParams.gravity = Gravity.CENTER;
        buttonParams.topMargin = dpToPx(activity, 60); // 在标题栏下方
        loginButton.setLayoutParams(buttonParams);
		
		
		

        loginButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
				  // 核心代码
					
				  
				  FileUtils.deleteFile("/storage/emulated/0/Download/zb.txt");

					
					//Toast.makeText(context, "登录按钮被点击", Toast.LENGTH_SHORT).show();
				}
			});

        // 将控件添加到分组框中
        groupBox.addView(menuTitle);     // 先添加文本
        groupBox.addView(collapseButton); // 再添加收缩按钮（在最上层）
        groupBox.addView(loginButton);   // 最后添加登录按钮

        // 将分组框添加到根布局中
        rootLayout.addView(groupBox);

        // 设置Activity的内容视图
        activity.setContentView(rootLayout);
    }
}
