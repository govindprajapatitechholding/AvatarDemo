package com.app.avatardemo

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.children
import com.app.avatardemo.adapter.AccessoriesAdapter
import com.app.avatardemo.adapter.CategoryAdapter
import com.app.avatardemo.model.AccessoriesItem
import com.app.avatardemo.model.CategoryItem
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field

class MainActivity : AppCompatActivity(), CategoryAdapter.OnCategoryClickListener,
    AccessoriesAdapter.OnAccessoriesClickListener {
    private lateinit var layoutParams: RelativeLayout.LayoutParams
    private var mCategoryAdapter: CategoryAdapter? = null
    private var mAccessoriesAdapter: AccessoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        mCategoryAdapter = CategoryAdapter(getCategories(), this)
        recCategories.adapter = mCategoryAdapter

        mCategoryAdapter?.mSelectedItem = mCategoryAdapter?.mList?.get(0)
        mCategoryAdapter?.notifyItemChanged(0)

        mAccessoriesAdapter = AccessoriesAdapter(getAccessories("face"), this)
        recAccessories.adapter = mAccessoriesAdapter
        mAccessoriesAdapter?.mSelectedItem = mAccessoriesAdapter?.mList?.get(0)
        mAccessoriesAdapter?.notifyItemChanged(0)

        mAccessoriesAdapter?.mSelectedItem?.let {
            addViewToRelativeLayout(it)
        }
    }

    private fun addViewToRelativeLayout(accessoriesItem: AccessoriesItem) {
        val imageView = AppCompatImageView(this)
        imageView.setImageResource(accessoriesItem.accessoriesResource)
        imageView.id = accessoriesItem.accessoriesResource
        imageView.tag = accessoriesItem.type
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.CENTER_IN_PARENT)
        imageView.layoutParams = layoutParams
        relativeLayout.addView(imageView)
    }

    private fun updateIfExists(accessoriesItem: AccessoriesItem) {
        val child = relativeLayout.children.find { it.tag == accessoriesItem.type }
        child?.let {
            it.id = accessoriesItem.accessoriesResource
            (it as AppCompatImageView).setImageResource(accessoriesItem.accessoriesResource)
            relativeLayout.invalidate()
        } ?: addViewToRelativeLayout(accessoriesItem)
    }

    private fun getCategories(): ArrayList<CategoryItem> {
        val list = arrayListOf<CategoryItem>()
        list.add(CategoryItem(1, "face", R.drawable.ic_face))
        list.add(CategoryItem(2, "eye", R.drawable.ic_eye))
        list.add(CategoryItem(3, "beard", R.drawable.ic_beard))
        list.add(CategoryItem(4, "hair", R.drawable.ic_hair))
        return list
    }

    private fun getAccessories(type: String): ArrayList<AccessoriesItem> {
        val drawables: Array<Field> =
            R.drawable::class.java.fields.filter { it.name.startsWith(type) }.toTypedArray()
        return ArrayList(drawables.map { AccessoriesItem(type, it.getInt(null)) })
    }

    override fun onCategoryItemClick(item: CategoryItem, position: Int) {
        mCategoryAdapter?.mSelectedItem = item
        mCategoryAdapter?.notifyDataSetChanged()

        mAccessoriesAdapter?.setList(getAccessories(item.categoryName))
    }

    override fun onAccessoriesItemClick(item: AccessoriesItem, position: Int) {
        mAccessoriesAdapter?.mSelectedItem = item
        mAccessoriesAdapter?.notifyDataSetChanged()
        updateIfExists(item)
    }
}