package nc.popup;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;

@Kroll.module(name="NcPopup", id="nc.popup")
public class NcPopupModule extends KrollModule {

	private static final String TAG = "NcPopupModule";
	public NcPopupModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.d(TAG, "inside onAppCreate");
	}

}