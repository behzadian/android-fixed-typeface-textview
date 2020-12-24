package farayan.commons.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import farayan.commons.components.font.icon.fixed.typeface.R
import java.lang.Exception
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

abstract class FixedResourceFontTextView : androidx.appcompat.widget.AppCompatTextView {

    private var attributedFontResID: Int? = null;

    open val inheritedFontResID: Int?
        get() {
            return null;
        }

    protected fun typefaceResID(): Int {
        if (attributedFontResID.isValuable() && inheritedFontResID.isValuable())
            throw Exception("Both attributedFontResID: ($attributedFontResID) and inheritedFontResID: ($inheritedFontResID) is provided");

        if (attributedFontResID == null && inheritedFontResID == null)
            throw Exception("None of attributedFontResID and inheritedFontResID are provided");

        return (attributedFontResID ?: inheritedFontResID)!!;
    }

    private fun Any?.isValuable(): Boolean {
        return this != null;
    }

    companion object {
        private var typeface: Typeface? = null;
        fun theFont(context: Context, fontResID: Int): Typeface {
            if (typeface == null) {
                typeface = ResourcesCompat.getFont(context, fontResID)
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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedResourceFontTextView, defStyleAttr, 0)
        try {
            attributedFontResID = typedArray.getResourceId(R.styleable.FixedResourceFontTextView_resourceFont, Int.MAX_VALUE)
            if (attributedFontResID == Int.MAX_VALUE)
                attributedFontResID = null;
            if (attributedFontResID != null)
                typeface = theFont(context, typefaceResID());
        } finally {
            typedArray.recycle();
        }
    }

    override fun setTypeface(tf: Typeface?) {
        if (tf != theFont(context, typefaceResID()))
            return;
        super.setTypeface(tf);
    }
}
