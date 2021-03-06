package com.example.jc.qrcodereader;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadQRCodeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadQRCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadQRCodeFragment extends Fragment implements ZBarScannerView.ResultHandler {
    private static final String TAG = "ReadQRCodeFragment";


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "onActivityResult");
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            Log.d(TAG, "got a scan result");
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            TextView myText = (TextView)getActivity().findViewById(R.id.myText);
            myText.setText(scanContent);

        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No Scan data", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void readQRCode() {

        Log.d(TAG, "readQRCode() entry");
        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());

        scanIntegrator.addExtra("SCAN_WIDTH", 400);
        scanIntegrator.addExtra("SCAN_HEIGHT", 100);
        scanIntegrator.addExtra("RESULT_DISPLAY_DURATION_MS", 90000L);
        scanIntegrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
        scanIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);


    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getContents()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        TextView myText = (TextView)getActivity().findViewById(R.id.myText);
        myText.setText(rawResult.getContents());
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private ZBarScannerView mScannerView;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadQRCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadQRCodeFragment newInstance(String param1, String param2) {
        Camera.open();
        ReadQRCodeFragment fragment = new ReadQRCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReadQRCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mScannerView = new ZBarScannerView(getActivity());    // Programmatically initialize the scanner view
        return mScannerView;

      //  return inflater.inflate(R.layout.fragment_readqrcode, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) this;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
