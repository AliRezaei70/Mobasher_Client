package ir.mobasher.app.client.adapter;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import ir.mobasher.app.client.R;

public class AdviserTypesExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> parentList; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> childList;
    HashMap<String, List<String>> childAdviseTypeId;

    public AdviserTypesExpandableListAdapter(Context context, List<String> parent, HashMap<String, List<String>> child, HashMap<String, List<String>> childAdviseTypeId) {
        this._context = context;
        this.parentList = parent;
        this.childList = child;
        this.childAdviseTypeId = childAdviseTypeId;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childList.get(this.parentList.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adviser_types_child, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.adviserTypesChild);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childList.get(this.parentList.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adviser_types_parent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.adviserTypesParent);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}