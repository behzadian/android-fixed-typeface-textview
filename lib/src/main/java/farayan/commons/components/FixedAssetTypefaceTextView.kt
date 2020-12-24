package farayan.commons.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import farayan.commons.components.font.icon.fixed.typeface.R
import java.lang.Exception

open class FixedAssetTypefaceTextView : androidx.appcompat.widget.AppCompatTextView {

    companion object {
        private var typeface: Typeface? = null;
        fun theFont(context: Context, fontPath: String): Typeface {
            if (typeface == null) {
                typeface = Typeface.createFromAsset(context.assets, fontPath);
            }
            return typeface!!;
        }
    }

    private var attributedFontPath: String? = null
    open val inheritedFontPath: String? = null

    protected fun typefacePath(): String {
        if (inheritedFontPath.isValuable() && attributedFontPath.isValuable())
            throw Exception("Both inheritedFontPath: ($inheritedFontPath) and attributedFontPath: ($attributedFontPath) is provided");

        if (inheritedFontPath.isNullOrBlank() && attributedFontPath.isNullOrBlank())
            throw Exception("None of inheritedFontPath and attributedFontPath are provided");

        return (inheritedFontPath ?: attributedFontPath)!!;
    }

    private fun String?.isValuable(): Boolean {
        return this != null && this.isNotEmpty();
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
            attributedFontPath = typedArray.getString(R.styleable.FixedAssetTypefaceTextView_assetFilePath)
                    ?: ""
            if (attributedFontPath.isValuable())
                typeface = theFont(context, typefacePath());
        } finally {
            typedArray.recycle();
        }
    }

    override fun setTypeface(tf: Typeface?) {
        if (tf != theFont(context, typefacePath()))
            return;
        super.setTypeface(tf);
    }
}
