package farayan.commons.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import farayan.commons.components.font.icon.fixed.typeface.R
import java.lang.Exception as Exception1

abstract class FixedResourceFontTextView : androidx.appcompat.widget.AppCompatTextView {

    private var initialized: Boolean = false
    private var attributedFontResID: Int? = null;

    open val inheritedFontResID: Int?
        get() {
            return null;
        }

    private fun typefaceResID(): Int {
        if (attributedFontResID.isValuable() && inheritedFontResID.isValuable())
            throw Exception1("Both attributedFontResID: ($attributedFontResID) and inheritedFontResID: ($inheritedFontResID) is provided");

        if (attributedFontResID == null && inheritedFontResID == null)
            throw Exception1("None of attributedFontResID and inheritedFontResID are provided");

        return (attributedFontResID ?: inheritedFontResID)!!;
    }

    private fun Any?.isValuable(): Boolean {
        return this != null;
    }


    private val fixedTypefaceFont: Typeface by lazy {
        ResourcesCompat.getFont(context, typefaceResID())!!
    }

    constructor(@androidx.annotation.NonNull context: Context) : super(context)
    constructor(@androidx.annotation.NonNull context: Context, attrs: AttributeSet?) : super(context, attrs) {
        loadAttrs(attrs)
    }

    constructor(@androidx.annotation.NonNull context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        loadAttrs(attrs, defStyleAttr)
    }


    private fun loadAttrs(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        initialized = true;
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedResourceFontTextView, defStyleAttr, 0)
        try {
            attributedFontResID = typedArray.getResourceId(R.styleable.FixedResourceFontTextView_resourceFont, Int.MAX_VALUE)
            if (attributedFontResID == Int.MAX_VALUE)
                attributedFontResID = null;
            typeface = fixedTypefaceFont;
        } finally {
            typedArray.recycle();
        }
    }

    override fun setTypeface(tf: Typeface?) {
        if (!initialized)
            return;
        if (tf != fixedTypefaceFont)
            return;
        super.setTypeface(tf)
    }
}
