package farayan.commons.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import farayan.commons.components.font.icon.awesome.brand.R
import java.lang.Exception
import kotlin.properties.Delegates

class FixedResourceFontTextView : androidx.appcompat.widget.AppCompatTextView {

    companion object {
        private var fontResID by Delegates.notNull<Int>();
        private var typeface: Typeface? = null;
        fun theFont(context: Context): Typeface {
            if (typeface == null) {
                typeface = ResourcesCompat.getFont(context, fontResID)
                        ?: throw Exception("ResourceID is not valid font");
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
            fontResID = typedArray.getResourceId(R.styleable.FixedResourceFontTextView_resourceFont, 0)
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
