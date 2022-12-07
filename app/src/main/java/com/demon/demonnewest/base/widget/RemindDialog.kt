import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.demon.base.BaseApp
import com.demon.base.utils.ext.*
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.DialogRemindBinding
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * @author DeMon
 * Created on 2021/11/24.
 * E-mail idemon_liu@qq.com
 */
class RemindDialog : DialogFragment() {
    private val TAG = "RemindDialog"
    private lateinit var title: String
    private var htmlContent: String? = null
    private var content: String? = null
    private lateinit var confirmText: String
    private lateinit var cancelText: String

    private var isOnlyConfirm: Boolean = false
    private var onConfirmListener: OnConfirmListener? = null
    private var onCancelListener: OnCancelListener? = null
    private var onKeyBackListener: OnCancelListener? = null
    private var onDismissListener: OnCancelListener? = null
    private var _isCancelable: Boolean = true
    private var gravity = Gravity.CENTER
    private var lineSpacingExtra = 0
    private var _binding: DialogRemindBinding? = null
    private val binding: DialogRemindBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupArgs()
        isCancelable = _isCancelable
        dialog?.setCanceledOnTouchOutside(_isCancelable)
        dialog?.setCancelable(_isCancelable)
        _binding = DialogRemindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.run {
            setGravity(Gravity.CENTER)
            setBackgroundDrawableResource(android.R.color.transparent)
            //设置边距，及宽高
            decorView.setPadding(40.intDp)
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        }
        binding.tvTitle.text = title
        htmlContent?.let {
            binding.tvContent.text = it.fromHtml()
        } ?: let {
            binding.tvContent.text = content
        }
        binding.tvContent.gravity = gravity
        if (lineSpacingExtra != 0) {
            binding.tvContent.setLineSpacing(lineSpacingExtra.dp, 1f)
        }
        if (isOnlyConfirm) {
            binding.tvCancel.visibility = View.GONE
            binding.horizontalDivider.visibility = View.GONE
        }
        binding.tvConfirm.text = confirmText
        binding.tvCancel.text = cancelText
        binding.tvCancel.setOnClickListener {
            if (onCancelListener == null) {
                dismissAllowingStateLoss()
            } else {
                onCancelListener?.onCancel(this)
            }
        }
        binding.tvConfirm.setOnClickListener {
            if (onConfirmListener == null) {
                dismissAllowingStateLoss()
            } else {
                onConfirmListener?.onConfirm(this)
            }
        }
        /**
         * 让用户按返回键时也触发 onCancel()
         * update by shaojunjie 2019.07.25
         */
        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && onKeyBackListener != null) {
                dismissAllowingStateLoss()
                onKeyBackListener?.onCancel(this)
                true
            }
            false
        }
        dialog?.setOnDismissListener {
            dismissAllowingStateLoss()
            onDismissListener?.onCancel(this)
        }
    }

    /**
     * 关闭弹窗的时候调用dismissAllowingStateLoss
     */
    fun showAllowingState(manager: FragmentManager) {
        showAllowingState(manager, tag)
    }

    fun showAllowingState(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }

    private fun setupArgs() {
        arguments ?: dismiss()
        val args = requireArguments()
        title = args.getString(TITLE) ?: ""
        htmlContent = args.getString(HTML_TITLE)
        content = args.getString(CONTENT)
        confirmText = args.getString(CONFIRM_TEXT) ?: ""
        cancelText = args.getString(CANCEL_TEXT) ?: ""
        isOnlyConfirm = args.getBoolean(IS_ONLY_CONFIRM)
        _isCancelable = args.getBoolean(IS_CANCELABLE)
        gravity = args.getInt(GRAVITY)
        lineSpacingExtra = args.getInt(LINE_SPACING_EXTRA)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val TITLE = "title"
        private const val HTML_TITLE = "htmlContent"
        private const val CONTENT = "content"
        private const val CONFIRM_TEXT = "confirm_text"
        private const val CANCEL_TEXT = "cancel_text"
        private const val IS_ONLY_CONFIRM = "is_only_confirm"
        private const val IS_CANCELABLE = "is_cancelable"
        private const val GRAVITY = "gravity"
        private const val LINE_SPACING_EXTRA = "lineSpacingExtra"
        fun newInstance(builder: Builder): RemindDialog {
            val content = builder.content
            val htmlContent = builder.htmlContent
            val args = Bundle()
            val fragment = RemindDialog()
            args.putString(TITLE, builder.title)
            args.putString(HTML_TITLE, builder.htmlContent)
            args.putString(CONTENT, builder.content)
            args.putString(CONFIRM_TEXT, builder.confirmText)
            args.putString(CANCEL_TEXT, builder.cancelText)
            args.putBoolean(IS_ONLY_CONFIRM, builder.isOnlyConfirm)
            args.putBoolean(IS_CANCELABLE, builder.isCancelable)
            args.putInt(GRAVITY, builder.gravity)
            args.putInt(LINE_SPACING_EXTRA, builder.lineSpacingExtra)
            fragment.arguments = args
            fragment.onConfirmListener = builder.onConfirmListener
            fragment.onCancelListener = builder.onCancelListener
            fragment.onKeyBackListener = builder.onKeyBackListener
            fragment.onDismissListener = builder.onDismissListener
            return fragment
        }
    }

    class Builder {
        internal var title: String
        internal var htmlContent: String? = null
        internal var content: String? = null
        internal var confirmText: String
        internal var cancelText: String
        internal var onConfirmListener: OnConfirmListener? = null
        internal var onCancelListener: OnCancelListener? = null
        internal var onKeyBackListener: OnCancelListener? = null
        internal var onDismissListener: OnCancelListener? = null
        internal var isOnlyConfirm: Boolean
        internal var isCancelable: Boolean
        internal var gravity: Int = Gravity.CENTER
        internal var lineSpacingExtra: Int = 0

        init {
            this.title = BaseApp.appContext.getString(R.string.tips)
            this.confirmText = BaseApp.appContext.getString(R.string.confirm)
            this.cancelText = BaseApp.appContext.getString(R.string.cancel)
            this.isOnlyConfirm = false
            this.isCancelable = true
        }

        fun setLineSpacingExtra(lineSpacingExtra: Int) = apply {
            this.lineSpacingExtra = lineSpacingExtra
        }

        fun setCustomHtmlContent(title: String): Builder {
            this.htmlContent = title
            return this
        }

        fun setCustomTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setCustomContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setContentGravity(gravity: Int): Builder {
            this.gravity = gravity;
            return this
        }

        fun setCustomConfirmText(confirmText: String): Builder {
            this.confirmText = confirmText
            return this
        }

        fun setCustomCancelText(cancelText: String): Builder {
            this.cancelText = cancelText
            return this
        }


        fun setOnConfirmListener(action: (DialogFragment) -> Unit): Builder {
            this.onConfirmListener = object : OnConfirmListener {
                override fun onConfirm(dialog: DialogFragment) {
                    action(dialog)
                }
            }
            return this
        }

        fun setOnCancelListener(action: (DialogFragment) -> Unit): Builder {
            this.onCancelListener = object : OnCancelListener {
                override fun onCancel(dialog: DialogFragment) {
                    action(dialog)
                }
            }
            return this
        }


        fun setOnKeyBackListener(action: (DialogFragment) -> Unit): Builder {
            this.onKeyBackListener = object : OnCancelListener {
                override fun onCancel(dialog: DialogFragment) {
                    action(dialog)
                }
            }
            return this
        }

        fun setOnDismissListener(action: (DialogFragment) -> Unit): Builder {
            this.onDismissListener = object : OnCancelListener {
                override fun onCancel(dialog: DialogFragment) {
                    action(dialog)
                }
            }
            return this
        }

        fun isOnlyConfirm(isOnlyConfirm: Boolean): Builder {
            this.isOnlyConfirm = isOnlyConfirm
            return this
        }

        fun isCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        fun build(): RemindDialog {
            return RemindDialog.newInstance(this)
        }
    }
}

interface OnConfirmListener {
    fun onConfirm(dialog: DialogFragment)
}

interface OnCancelListener {
    fun onCancel(dialog: DialogFragment)
}


/**
 * ```kotlin
 * lifecycleScope.launch {
 *     val isConfirm = supportFragmentManager.showRemindDialog(
 *         title =getString(R.string.tv_promote_reminder_two),
 *         content = getString(R.string.tv_has_save_change),
 *         cancelText = getString(R.string.tv_exit_directly),
 *         cancelable = false,
 *         tag = this@GeneralizeSettingsActivity.javaClass.name
 *     )
 *     if (isConfirm) {
 *         submit()
 *         return@launch
 *     }
 *     finish()
 * }
 * ```
 * @param title 弹窗标题，为空的时候使用默认
 * @param content 弹窗内容，content和htmlContent两者必须有一个内容，否则会报错
 * @param htmlContent html的弹窗内容，content和htmlContent两者必须有一个内容，否则会报错
 * @param cancelText 取消按钮文字，为空的时候使用默认
 * @param confirmText 确定按钮文字，为空的时候使用默认
 * @param cancelable 点击外部是否关闭弹窗，默认点击关闭
 * @param onlyConfirm 是否只显示确定按钮，默认false
 * @param canKeyBack 点击返回按钮是否关闭弹窗，默认false
 * @param canDismiss 点击空白是否关闭弹窗，默认false
 * @param tag tag必填，用类的全路径名，包名加类名，javaClass.name，如果单纯用simpleName的话可能会有类名一样的
 * @since content和htmlContent两者必须有一个内容，否则会报错
 * @return true表示点击了confirm，false表示关闭，如果canKeyBack是true的话点击返回键关闭也会是返回false，
 * 如果是返回键关闭和点击关闭按钮逻辑不一样的话需要另写代码
 */
suspend fun FragmentManager.showRemindDialog(
    title: String? = null,
    content: String? = null,
    cancelText: String? = null,
    confirmText: String? = null,
    htmlContent: String? = null,
    cancelable: Boolean = true,
    onlyConfirm: Boolean = false,
    canKeyBack: Boolean = false,
    canDismiss: Boolean = false,
    gravity: Int = Gravity.CENTER,
    tag: String
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        RemindDialog.Builder().apply {
            title?.let(::setCustomTitle)
            content?.let(::setCustomContent)
            cancelText?.let(::setCustomCancelText)
            confirmText?.let(::setCustomConfirmText)
            htmlContent?.let(::setCustomHtmlContent)
            isCancelable(cancelable)
            isOnlyConfirm(onlyConfirm)
            setContentGravity(gravity)
            setOnConfirmListener {
                it.dismissAllowingStateLoss()
                continuation.resumeWith(Result.success(true))
            }
            setOnCancelListener {
                it.dismissAllowingStateLoss()
                continuation.resumeWith(Result.success(false))
            }
            if (canKeyBack) {
                setOnKeyBackListener {
                    it.dismissAllowingStateLoss()
                    if (!continuation.isCompleted) {
                        continuation.resumeWith(Result.success(false))
                    }
                }
            }
            if (canDismiss) {
                setOnDismissListener {
                    it.dismissAllowingStateLoss()
                    if (!continuation.isCompleted) {
                        continuation.resumeWith(Result.success(false))
                    }
                }
            }
        }.build().showAllowingState(this, tag)
    }
}
