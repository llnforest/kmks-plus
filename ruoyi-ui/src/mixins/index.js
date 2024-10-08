export const rightClick = {
  data() {
    return {
      activeRow: {},
      rightMenuVisible: false,
      rightMenuLeft: '0px',
      rightMenuTop: '0px',
      resizeTop: -85,
      resizeLeft: 5
    }
  },
  watch: {
    rightMenuVisible(value) {
      if (value) {
        document.body.addEventListener('click', this.closeMenu)
      } else {
        document.body.removeEventListener('click', this.closeMenu)
      }

    }
  },
  computed: {},

  methods: {
    isCourse(course) {
      return this.$store.getters.configCourses.split(",").includes(course)
    },
    isJgType(jgType) {
      return this.$store.getters.configJgType == jgType
    },
    // 表格右击的功能
    rightClick(row, column, event) {
      event.preventDefault();
      // 根据事件对象中鼠标点击的位置，进行定位
      const offsetLeft = this.$el.getBoundingClientRect().left
      this.rightMenuLeft = event.clientX - offsetLeft + this.resizeLeft + 'px';
      this.rightMenuTop = event.clientY + this.resizeTop + 'px';
      // 改变自定义菜单的隐藏与显示
      this.activeRow = row;
      this.rightMenuVisible = true;
    },
    rowClick(row, column, event) {
      if (this.$refs.dataTable !== undefined) {
        this.$refs.dataTable.toggleRowSelection(row);

      }
    },
    closeMenu() {
      this.rightMenuVisible = false;
    }
  }
}
