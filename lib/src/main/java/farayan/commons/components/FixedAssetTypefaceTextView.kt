package farayan.commons.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import farayan.commons.components.font.icon.awesome.brand.R
import java.lang.Exception

class FixedAssetTypefaceTextView : androidx.appcompat.widget.AppCompatTextView {

    companion object {
        private lateinit var fontPath: String;
        private var typeface: Typeface? = null;
        fun theFont(context: Context): Typeface {
            if (typeface == null) {
                typeface = Typeface.createFromAsset(context.assets, fontPath);
            }
            return typeface!!;
        }
    }

    constructor(@androidx.annotation.NonNull context: Context) : super(context)
    constructor(@androidx.annotation.NonNull context: Context, attrs: AttributeSet?) : super(context, attrs) {
        loadAttrs(attrs)
    }

    constructor(@androidx.annotation.NonNull context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        loadAttrs(attrs, defStyleAttr)
    }


    private fun loadAttrs(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedAssetTypefaceTextView, defStyleAttr, 0)
        try {
            fontPath = typedArray.getString(R.styleable.FixedAssetTypefaceTextView_assetFilePath)
                    ?: throw Exception("assetFilePath is not set");
            typeface = theFont(context);
        } finally {
            typedArray.recycle();
        }
    }

    override fun setTypeface(tf: Typeface?) {
        if (tf != theFont(context))
            return;
        super.setTypeface(tf);
    }
}
