package com.example.test2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test2.model.Message;
import com.example.test2.model.PullParser;
import com.example.test2.widget.CircleImageView;
import java.io.InputStream;
import java.util.List;

/**
 * 大作业:实现一个抖音消息页面,
 * 1、所需的data数据放在assets下面的data.xml这里，使用PullParser这个工具类进行xml解析即可
 * <p>如何读取assets目录下的资源，可以参考如下代码</p>
 * <pre class="prettyprint">
 *
 *         @Override
 *     protected void onCreate(@Nullable Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_xml);
 *         //load data from assets/data.xml
 *         try {
 *             InputStream assetInput = getAssets().open("data.xml");
 *             List<Message> messages = PullParser.pull2xml(assetInput);
 *             for (Message message : messages) {
 *
 *             }
 *         } catch (Exception exception) {
 *             exception.printStackTrace();
 *         }
 *     }
 * </pre>
 * 2、所需UI资源已放在res/drawable-xxhdpi下面
 *
 * 3、作业中的会用到圆形的ImageView,可以参考 widget/CircleImageView.java
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            InputStream assetInput = getAssets().open("data.xml");
            List<Message> messages = PullParser.pull2xml(assetInput);
            RecyclerView rv = findViewById(R.id.rv_list);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(new _Adapter(messages));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public class _Adapter extends RecyclerView.Adapter<_Adapter.ViewHolder> {
        private List<Message> ml;

        public _Adapter(List<Message> list) {
            this.ml = list;
        }

        @Override
        public int getItemCount() {
            return this.ml.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup vg, int vt) {
            final ViewHolder vh = new ViewHolder(LayoutInflater.from(vg.getContext()).inflate(R.layout.im_list_item, vg, false));
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = vh.getAdapterPosition();
                    startActivity(new Intent(MainActivity.this, Chatroom.class).putExtra("pos", pos));
                }
            });
            return vh;
        }

        @Override
        public  void onBindViewHolder(ViewHolder vh, int pos) {
            Message mg = this.ml.get(pos);
            switch (mg.getIcon()) {
                case "TYPE_ROBOT":
                    vh.civ.setImageResource(R.drawable.session_robot);
                    break;
                case "TYPE_SYSTEM":
                    vh.civ.setImageResource(R.drawable.session_system_notice);
                    break;
                case "TYPE_USER":
                    vh.civ.setImageResource(R.drawable.icon_girl);
                    break;
                case "TYPE_STRANGER":
                    vh.civ.setImageResource(R.drawable.session_stranger);
                    break;
                case "TYPE_GAME":
                    vh.civ.setImageResource(R.drawable.icon_micro_game_comment);
                    break;
                default:
                    break;
            }
            vh.iv.setVisibility(mg.isOfficial() ? View.VISIBLE:View.INVISIBLE);
            vh.tv1.setText(mg.getTitle());
            vh.tv2.setText(mg.getDescription());
            vh.tv3.setText(mg.getTitle());
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView civ;
            ImageView iv;
            TextView tv1, tv2, tv3;

            public ViewHolder (View view) {
                super(view);
                tv1 = view.findViewById(R.id.tv_title);
                tv2 = view.findViewById(R.id.tv_description);
                tv3 = view.findViewById(R.id.tv_time);
                civ = view.findViewById(R.id.iv_avatar);
                iv = view.findViewById(R.id.robot_notice);
            }
        }
    }
}
