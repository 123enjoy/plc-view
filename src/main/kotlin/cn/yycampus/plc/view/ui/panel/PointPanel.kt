package cn.yycampus.plc.view.ui.panel

import cn.hutool.core.date.DateTime
import cn.hutool.core.date.DateUtil
import cn.hutool.core.lang.Tuple
import cn.hutool.core.thread.ThreadUtil
import cn.hutool.core.util.NumberUtil
import cn.hutool.core.util.ReflectUtil
import cn.hutool.log.Log
import cn.yycampus.plc.core.enity.PLCEntity
import cn.yycampus.plc.core.enity.PLCPointEntity
import cn.yycampus.plc.core.enums.DataTypeEnum
import cn.yycampus.plc.view.enums.PLCEnum
import cn.yycampus.plc.view.util.x
import net.miginfocom.swing.MigLayout
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.time.TimeSeries
import org.jfree.data.time.TimeSeriesCollection
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.table.DefaultTableModel

class PointPanel(private val config: PLCEntity, private val entity: PLCPointEntity) : TabPanel() {
    private val logger:Log = Log.get(PointPanel::class.java)
    private val plc = PLCEnum.convert(config.name)
    private val connection = ReflectUtil.newInstance(plc.connect)

    private var isRunning = true

    init {
        connection.initEntity(config)
        if (!connection.connect()) throw Exception("Connect Fail....")
    }

    private val contentSpilt = JSplitPane(JSplitPane.HORIZONTAL_SPLIT)

    private val array = arrayOf(arrayOf<String>())
    private val tableLeftPanel = JPanel(BorderLayout())

    private val chartDataset = mutableListOf<Tuple>()

    private val serial = TimeSeries(entity.title)

    private val column = arrayOf("Index", "Time", "Value")

    private val tableModel = DefaultTableModel(array, column)
    private val table = JTable()
    private val northPanel = JPanel(MigLayout("hidemode 3", "[fill][fill][fill][fill][fill]", "[]"))

    private val stop = JButton("Stop Refresh").apply {
        preferredSize = 80 x 40
        addActionListener {
            stopAutoRefresh()
        }
    }

    private val rightLeftPanel = JSplitPane(JSplitPane.VERTICAL_SPLIT)

    private val dataset = TimeSeriesCollection()

    private val chart: JFreeChart

    private var currentPos: Int = 0

    private var isUpdate = false

    private val showCount = 10

    init {
        add(northPanel, BorderLayout.NORTH)

        add(contentSpilt, BorderLayout.CENTER)
//        add(refresh, BorderLayout.NORTH)

        contentSpilt.add(tableLeftPanel)
        contentSpilt.add(rightLeftPanel)

        tableLeftPanel.add(JScrollPane(table), BorderLayout.CENTER)
        northPanel.add(stop, "cell 1 0")

        table.model = tableModel

        table.isEnabled = false

        dataset.addSeries(serial)
        // 创建JFreeChart对象
        chart = ChartFactory.createTimeSeriesChart(
            entity.title,
            "time",
            "Value",
            dataset, //数据集
            false, true, false
        )
        val chartPanel = ChartPanel(chart)

        chartPanel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        rightLeftPanel.add(chartPanel)
        chartPanel.addMouseWheelListener {
            if (isUpdate) return@addMouseWheelListener
            if (it.wheelRotation > 0) {
                if (currentPos < (chartDataset.size - 1)) {
                    val currentDataset = DefaultCategoryDataset()
                    currentPos++
                    (currentPos..<chartDataset.size).forEach { item ->
                        currentDataset.addValue(NumberUtil.parseDouble(chartDataset[item].get<Any>(0).toString(),0.0)
                            ,entity.title, chartDataset[item][1])
                    }
                    chart.categoryPlot.dataset = currentDataset
                }
            } else {
                if (currentPos > 0) {
                    currentPos--
                    val currentDataset = DefaultCategoryDataset()
                    (currentPos..<chartDataset.size).forEach { item ->
                        currentDataset.addValue(NumberUtil.parseDouble(chartDataset[item].get<Any>(0).toString(),0.0)
                            ,entity.title, chartDataset[item][1])
                    }
                    chart.categoryPlot.dataset = currentDataset

                }
            }
        }

        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        ThreadUtil.execAsync {
            while (isRunning) {
                try {
                    val value =
                        DataTypeEnum.convert(entity.type).parser.readValue(connection, entity.db)
                    val tuple = Tuple(tableModel.columnCount + 1, DateUtil.now().toString(), value)
                    chartDataset.add(tuple)
                    if (chartDataset.size > showCount) {
                        chartDataset.removeAt(0)
                    }

                    (chart.categoryPlot.dataset as DefaultCategoryDataset).addValue(
                        value.toString().toDouble(), entity.title, tuple[1]
                    )
                    tableModel.addRow(tuple.members)
                }catch (e:Exception){
                    logger.info(e)
                }

                ThreadUtil.sleep(1000)
            }
        }
    }

    override fun close() {
        isRunning = false
    }
}