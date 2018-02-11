<%@ taglib uri="/struts-tags" prefix="s" %>


	<div class="navbar-wrapper">
      <div class="container">

        <nav class="navbar navbar-inverse navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">Project name</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="userList.action">User</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Product <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="ccList.action">Custom Component</a></li>
                    <li><a href="collectionList.action">Collection</a></li>
                    <li class="divider"></li>
                    <li><a href="productList.action">Product</a></li>
                    <li><a href="aeProductOrder.action">Product Order</a></li>
                  </ul>
                </li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Shopping <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="methodShippingList.action">Method Shipping</a></li>
                    <li><a href="taxesList.action">Taxes</a></li>
                    <li class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="shoppingCartList.action">ShoppingCarts</a></li>
                  </ul>
                </li>
                 <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Coupons<span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="couponManagerList.action">Manager</a></li>
                    <li><a href="couponList.action">Coupon List</a></li>
                  </ul>
                </li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Tools <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="resetCacheProduct.action">Reload products</a></li>
                    <li><a href="resetCacheDefault.action">Reload default</a></li>
                    <li><a href="resetCollections.action">Reload Collections</a></li>
                    <li><a href="resetCacheTranslations.action">Reload translations</a></li>
                    <li><a href="dataminingList.action">Data Mining</a></li>
                    <li><a href="auditList.action">Audit</a></li>
                    <li><a href="logList.action">Logs</a></li>
                    <li><a href="jobsList.action">Jobs</a></li>
                    <li><a href="translationList.action">Translations</a></li>
                  </ul>
                </li>
                <s:if test="#isLogin">	
			    	<li><a href="../user/logout.action">Log Out</a></li>
			    </s:if>
			    <s:else>
			    	<li><a id="sign_in_link" href="signInPop.action?ajax=true"><s:text name="bolsos.common.title.signIn"/></a></li>
			    </s:else>
              </ul>
            </div>
          </div>
        </nav>

      </div>
    </div>
