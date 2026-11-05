<style scoped>
	.loading_animation {
	  width: 50px;
	  height: 50px;
	  border: 5px solid #eee;
	  border-left-color: #e27a61;
	  border-radius: 50%;
	  animation: loading-animate 1s linear infinite;
	  transform: translate(-50%, -50%);
	  position: absolute;
	  left: 50%;
	  top: 50%;
	}
	@keyframes loading-animate {
	  0% {
	    transform: rotate(0deg);
	  }
	  100% {
	    transform: rotate(360deg);
	  }
	}
	.loading_floating{
	    width: 100%;
	    height: 100%;
	    position: fixed;
	    top: 0px;
	    left: 0px;
	    bottom: 0px;
	    right: 0px;
	    background-color: rgba(0,0,0,.2);
	    z-index: 9999999;
	}
</style>
<template>
  <div class="loading_floating " @click.stop="bgClick()" v-show="isSpinShow"><div class="loading_animation"></div></div>
</template>

<script>
export default {
	name: 'test',
	data() {
	return {
		isSpinShow: false
	}
	},
	methods: {
		show(){
			this.isSpinShow = true;
		},
		hide(){
			this.isSpinShow = false;
		},
		bgClick(){
		}
	}
}
</script>
