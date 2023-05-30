// Model
const LEVEL3_EXP = 20000;
const LEVEL2_EXP = 5000;
const LEVEL1_EXP = 1000;
const characterUpgradeData = [0, 0, 200, 500, 1000, 1600, 2650, 4320, 6640, 9700, 13560, 18300, 24010,
    30740, 38570, 47570, 57810, 69360, 82280, 96640, 112510, 129090, 145930, 163030, 180400, 198040,
    215950, 234140, 252620, 271380, 290420, 309760, 329390, 349320, 369540, 390070, 410910, 432050,
    453500, 475260, 497340, 519730, 545280, 574420, 607250, 643850, 684320, 728750, 777230, 829860,
    886730, 947920, 1013540, 1083670, 1158410, 1237860, 1322100, 1411220, 1505330, 1604520, 1708870,
    1815110, 1926940, 2044470, 2167790, 2297000, 2432200, 2573490, 2720970, 2874750, 3034920, 3214180,
    3414100, 3635020, 3877280, 4141210, 4427160, 4735460, 5066460, 5420500, 5797920];
const expMaterialData = [18000, 24000, 30000, 37000, 45000];
const expCreditMaterialData = [500, 600, 800, 1000, 1200];
const creditMaterialData = [9500, 12500, 16000, 20000, 24000];
const ascensionData = [[0, 3200, 9600, 22400, 54400, 118400, 246400], [0, 4000, 12000, 28000, 68000, 148000, 308000]];
const ascensionTable = [0, 0, 1, 2, 3, 4, 5, 6];
let cumulativeEXP = 0;
let cumulativeCredit = 0;
let existLevel3 = 0;
let existLevel2 = 0;
let existLevel1 = 0;
let existCredit = 0;
let balanceLevel = 1;
let isAscension = false;
var model = {
    reset: function () {
        cumulativeEXP = 0;
        cumulativeCredit = 0;
        existLevel3 = 0;
        existLevel2 = 0;
        existLevel1 = 0;
        existCredit = 0;
        balanceLevel = 1;
        isAscension = false;
    },
    refreshAscension: function (ascension) {
        if (ascension == "true") {
            isAscension = true;
        } else {
            isAscension = false;
        }
    },
    refreshBalanceLevel: function (balanceLevel) {
        worldLevel = balanceLevel;
    },
    refreshMaterial: function (level3material, level2material, level1material, credit) {
        existLevel3 = level3material;
        existLevel2 = level2material;
        existLevel1 = level1material;
        existCredit = credit;
    },
    getToTalEXP: function (currLevel, targetLevel) {
        cumulativeEXP += characterUpgradeData[targetLevel] - characterUpgradeData[currLevel];
        console.log("cumulativeEXP" + cumulativeEXP);
        return cumulativeEXP;
    },
    getTotalCredit: function (star, currLevel, targetLevel) {
        let ascensionCost = 0;
        let max = Math.min(Math.floor((targetLevel - 1) / 10), 7);
        let min = isAscension ? Math.min(Math.floor((currLevel - 1) / 10), 7) + 1 : Math.min(Math.floor((currLevel - 1) / 10), 7);
        // console.log("max" + max);
        // console.log("min" + min);
        if (star == 4) {
            ascensionCost = ascensionData[0][ascensionTable[max]] - ascensionData[0][ascensionTable[min]];
        } else {
            ascensionCost = ascensionData[1][ascensionTable[max]] - ascensionData[1][ascensionTable[min]];
        }
        // console.log("ascensionCost" + ascensionCost);
        let credit = Math.ceil(cumulativeEXP * 0.1 + ascensionCost);
        // console.log("credit" + credit);
        cumulativeCredit += credit;
        console.log("cumulativeCredit" + cumulativeCredit);
        return cumulativeCredit;
    },
    getRequiredEXP: function () {
        return Math.max(cumulativeEXP - existLevel1 * LEVEL1_EXP - existLevel2 * LEVEL2_EXP - existLevel3 * LEVEL3_EXP, 0);
    },
    getLevel3Material: function () {
        if (this.getRequiredEXP() == 0) {
            return 0;
        }
        return Math.floor(this.getRequiredEXP() / LEVEL3_EXP);
    },
    getLevel2Material: function () {
        if (this.getRequiredEXP() == 0) {
            return 0;
        }
        return Math.floor((this.getRequiredEXP() % LEVEL3_EXP) / LEVEL2_EXP);
    },
    getLevel1Material: function () {
        if (this.getRequiredEXP() == 0) {
            return 0;
        }
        return Math.ceil(((this.getRequiredEXP() % LEVEL3_EXP) % LEVEL2_EXP) / LEVEL1_EXP);
    },
    getMemResult: function () {
        // console.log("getRequiredEXP" + this.getRequiredEXP());
        if (this.getRequiredEXP() == 0) {
            return 0;
        }
        return Math.ceil(this.getRequiredEXP() / expMaterialData[worldLevel >= 4 ? 4 : worldLevel]);
    }
    ,
    getRequiredCredit: function () {
        // console.log("cumulativeCredit" + cumulativeCredit);
        return Math.max(cumulativeCredit - existCredit - this.getMemResult() * expCreditMaterialData[worldLevel >= 4 ? 4 : worldLevel], 0);
    },
    getTreasureResult: function () {
        // console.log("getRequiredCredit" + this.getRequiredCredit());
        if (this.getRequiredCredit() == 0) {
            return 0;
        }
        var index = worldLevel >= 4 ? 4 : worldLevel;
        // console.log("index" + index);
        // console.log("creditMaterialData[worldLevel >= 4 ? 4 : worldLevel]" + creditMaterialData[index]);
        return Math.ceil(this.getRequiredCredit() / creditMaterialData[index]);
    },
    getTrailblazeResult: function () {
        // console.log("getMemResult" + this.getMemResult());
        // console.log("getTreasureResult" + this.getTreasureResult());
        return 10 * this.getMemResult() + 10 * this.getTreasureResult();
    }
};

// View
var view = {
    getExistlevel3material: function () {
        // console.log("L3M: " + document.getElementById("Input 1").value);
        return parseInt(document.getElementById("Input 1").value);
    },
    getExistlevel2material: function () {
        // console.log("L2M:" + document.getElementById("Input 2").value);
        return parseInt(document.getElementById("Input 2").value);
    },
    getExistlevel1material: function () {
        // console.log("L1M:" + document.getElementById("Input 3").value);
        return parseInt(document.getElementById("Input 3").value);
    },
    getExistCredit: function () {
        // console.log("Credit:" + document.getElementById("Input 4").value);
        return parseInt(document.getElementById("Input 4").value);
    },
    getBalanceLevel: function () {
        // console.log("BLEVEL:" + document.getElementById("balanceLevel").value);
        return parseInt(document.getElementById("balanceLevel").value);
    },
    getCurrLevel: function () {
        // console.log("CL:" + document.getElementById("currentLevel").value);
        return parseInt(document.getElementById("currentLevel").value);
    },
    getTargetLevel: function () {
        // console.log("TL:" + document.getElementById("targetLevel").value);
        return parseInt(document.getElementById("targetLevel").value);
    },
    getAscension: function () {
        if (document.getElementById("isAscension").value == 1) {
            // console.log("true");
            return "true";
        } else {
            // console.log("false");
            return "false";
        }
    },
    getStar: function () {
        // console.log("star" + document.getElementById("star").value);
        return parseInt(document.getElementById("star").value);
    },
    setEnv: function (ascension, balanceLevel, level3material, level2material, level1material, credit) {
        model.refreshAscension(ascension);
        model.refreshBalanceLevel(balanceLevel);
        model.refreshMaterial(level3material, level2material, level1material, credit);
    },
    calculate: function (star, currLevel, targetLevel) {
        model.getToTalEXP(currLevel, targetLevel);
        model.getTotalCredit(star, currLevel, targetLevel);
    },
    setResult: function (level3material, level2material, level1material, creditResult, memResult, treasureResult, trailblazeResult) {
        document.getElementById("level3result").value = level3material;
        document.getElementById("level2result").value = level2material;
        document.getElementById("level1result").value = level1material;
        document.getElementById("creditResult").value = creditResult;
        document.getElementById("memResult").value = memResult;
        document.getElementById("treasureResult1").value = treasureResult;
        document.getElementById("Trailblaze").value = trailblazeResult;
    }
};

// Controller
var controller = {
    submitButtonClicked: function () {
        var ascension = view.getAscension();
        var balanceLevel = view.getBalanceLevel();
        var level3material = view.getExistlevel3material();
        var level2material = view.getExistlevel2material();
        var level1material = view.getExistlevel1material();
        var credit = view.getExistCredit();
        var star = view.getStar();
        var currLevel = view.getCurrLevel();
        var targetLevel = view.getTargetLevel();
        if (ascension != "true" && ascension != "false") {
            alert("请选择是否突破！");
            return;
        }
        if (balanceLevel != 0 && balanceLevel != 1 && balanceLevel != 2 && balanceLevel != 3 && balanceLevel != 4 && balanceLevel != 5 && balanceLevel != 6) {
            alert("请选择平衡等级！");
            return;
        }
        if (isNaN(level3material)) {
            alert("请输入漫游指南数量！");
            return;
        }
        if (isNaN(level2material)) {
            alert("请输入旅行记录数量！");
            return;
        }
        if (isNaN(level1material)) {
            alert("请输入旅情见闻数量！");
            return;
        }
        if (isNaN(credit)) {
            alert("请输入信用点数量！");
            return;
        }
        if (star == "") {
            alert("请选择星级！");
            return;
        }
        if (currLevel == "") {
            alert("请输入当前等级！");
            return;
        }
        if (targetLevel == "") {
            alert("请输入目标等级！");
            return;
        }

        if (currLevel > targetLevel) {
            alert("目标等级不能小于当前等级！");
            return;
        }
        if (level3material < 0) {
            alert("材料数量不能为负数！");
            return;
        }
        if (level2material < 0) {
            alert("材料数量不能为负数！");
            return;
        }
        if (level1material < 0) {
            alert("材料数量不能为负数！");
            return;
        }
        if (credit < 0) {
            alert("信用点不能为负数！");
            return;
        }
        // console.log("submit:");
        // console.log("Level3:" + level3material);
        // console.log("Level2:" + level2material);
        // console.log("Level1:" + level1material);
        // console.log("Credit:" + credit);
        // console.log("Star:" + star);
        // console.log("CurrLevel:" + currLevel);
        // console.log("TargetLevel:" + targetLevel);
        // console.log("Ascension:" + ascension);
        view.setEnv(ascension, balanceLevel, level3material, level2material, level1material, credit);
        view.calculate(star, currLevel, targetLevel);
        view.setResult(model.getLevel3Material(), model.getLevel2Material(), model.getLevel1Material(), model.getRequiredCredit(),
            model.getMemResult(), model.getTreasureResult(), model.getTrailblazeResult());
        var currentLevel = document.getElementById("currentLevel");
        var targetLevel = document.getElementById("targetLevel");
        var star = document.getElementById("star");
        var ascension = document.getElementById("isAscension");
        currentLevel.selectIndex = 0;
        targetLevel.selectIndex = 0;
        star.selectIndex = 0;
        ascension.selectIndex = 0;
        var balanceLevelX = document.getElementById("balanceLevel");
        var level3materialX = document.getElementById("Input 1");
        var level2materialX = document.getElementById("Input 2");
        var level1materialX = document.getElementById("Input 3");
        var creditX = document.getElementById("Input 4");
        balanceLevelX.disabled  = true;
        level3materialX.disabled  = true;
        level2materialX.disabled  = true;
        level1materialX.disabled  = true;
        creditX.disabled  = true;
    },
    resetButtonClicked: function () {
        var balanceLevel = document.getElementById("balanceLevel");
        var level3material = document.getElementById("Input 1");
        var level2material = document.getElementById("Input 2");
        var level1material = document.getElementById("Input 3");
        var credit = document.getElementById("Input 4");
        balanceLevel.disabled  = false;
        level3material.disabled  = false;
        level2material.disabled  = false;
        level1material.disabled  = false;
        credit.disabled  = false;
        document.getElementById("Input 1").value = "";
        document.getElementById("Input 2").value = "";
        document.getElementById("Input 3").value = "";
        document.getElementById("Input 4").value = "";
        document.getElementById("level3result").value = "";
        document.getElementById("level2result").value = "";
        document.getElementById("level1result").value = "";
        document.getElementById("creditResult").value = "";
        document.getElementById("memResult").value = "";
        document.getElementById("treasureResult1").value = "";
        document.getElementById("Trailblaze").value = "";
        document.getElementById("balanceLevel").value = 0;
        document.getElementById("currentLevel").value = 1;
        document.getElementById("targetLevel").value = 1;
        document.getElementById("star").value = 4;
        document.getElementById("isAscension").value = 0;
        model.reset();
    }
};

// Event listeners
document.getElementById("button1").addEventListener("click", controller.submitButtonClicked);
document.getElementById("button2").addEventListener("click", controller.resetButtonClicked);