package nc.popup;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.proxy.MenuItemProxy;
import org.appcelerator.titanium.proxy.TiViewProxy;

import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;

@Kroll.proxy(creatableInModule=NcPopupModule.class)
public class PopupMenuProxy extends KrollProxy implements OnMenuItemClickListener, OnDismissListener{

	private static final String TAG = "NcPopupModule";
	
	private static final int MSG_FIRST_ID = KrollProxy.MSG_LAST_ID + 1;
	private static final int MSG_SHOW = MSG_FIRST_ID + 100;
	protected static final int MSG_LAST_ID = MSG_FIRST_ID + 999;
	
	private static final String PROPERTY_OPTIONS = "options";
	private static final String PROPERTY_VIEW = "view";
	private static final String EVENT_CLICK = "click";
	private static final String EVENT_DISMISS = "dismiss";
	
	private PopupMenu mPopupMenu;
	
	public PopupMenuProxy() {
		super();
	}
	
	public PopupMenuProxy(TiContext tiContext) {
		this();
	}

	@Override
	public void handleCreationDict(KrollDict dict) {
		super.handleCreationDict(dict);
		
		String[] options = null;
		View view = null;
		
		if (dict.containsKey(PROPERTY_OPTIONS)) {
			options = dict.getStringArray(PROPERTY_OPTIONS);
		}
		if (options == null) {
			throw new IllegalArgumentException("'options' is required");
		}
		
		if (dict.containsKey(PROPERTY_VIEW)) {
			Object v = dict.get(PROPERTY_VIEW);
			if (v instanceof TiViewProxy) {
				TiViewProxy vp = (TiViewProxy) v;
				view = vp.getOrCreateView().getOuterView();
			}
			
			if (v instanceof MenuItemProxy) {
				view = getActivity().findViewById(((MenuItemProxy) v).getItemId());
			}
		}
		if (view == null) {
			throw new IllegalArgumentException("'view' is required");
		}
		
		mPopupMenu = new PopupMenu(getActivity(), view);
		
		mPopupMenu.setOnMenuItemClickListener(this);
		mPopupMenu.setOnDismissListener(this);
		
		int i = 0;
		for (String option : options) {
			mPopupMenu.getMenu().add(Menu.NONE, Menu.NONE, i++, option);
		}
		
	}

	@Kroll.method
	public void show() {
		if (TiApplication.isUIThread()) {
			handleShow();
		} else {
			getMainHandler().obtainMessage(MSG_SHOW).sendToTarget();
		}
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case MSG_SHOW:
				handleShow();
				return true;
		}
		return super.handleMessage(msg);
	}

	private void handleShow() {
		mPopupMenu.show();
	}

	@Override
	public void onDismiss(PopupMenu menu) {
		if (hasListeners(EVENT_DISMISS)) {
			fireEvent(EVENT_DISMISS, null);
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		if (hasListeners(EVENT_CLICK)) {
			KrollDict data = new KrollDict();
			data.put("index", item.getOrder());
			fireEvent(EVENT_CLICK, data);
		}
		return true;
	}

}