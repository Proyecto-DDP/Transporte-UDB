package proyecto.transporte.udb;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Context;

public class routeAdapter extends RecyclerView.Adapter<routeAdapter.viewHolder> {
    Context context;
    ArrayList<itemModel> arrayList;
    String[] provisional;
    String placa;

    public routeAdapter(Context context, ArrayList<itemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public routeAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.route_card, viewGroup, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(routeAdapter.viewHolder viewHolder, int position) {
        viewHolder.routeName.setText(arrayList.get(position).getRouteN());
        viewHolder.routeType.setText(arrayList.get(position).getType());
        viewHolder.routeImg.setImageResource(arrayList.get(position).getImage());
        viewHolder.status = arrayList.get(position).getStatus();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView routeImg;
        TextView routeName, routeType;
        FrameLayout statusColor;
        String status;
        //Botón para ir a la siguiente página
        Button ver_info;

        public viewHolder(View itemView) {
            super(itemView);
            routeImg = (ImageView) itemView.findViewById(R.id.media_image);
            routeName = (TextView) itemView.findViewById(R.id.primary_text);
            routeType = (TextView) itemView.findViewById(R.id.sub_text);
            ver_info = (Button) itemView.findViewById(R.id.ver_info);
            statusColor = (FrameLayout) itemView.findViewById(R.id.status_color);

            if (status != "") {

                statusColor.setBackgroundColor(Color.parseColor("#00701a"));
            }

            ver_info.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ver_info:
                    Intent intent = new Intent(view.getContext(), show_info.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("RUTA", routeName.getText());
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
