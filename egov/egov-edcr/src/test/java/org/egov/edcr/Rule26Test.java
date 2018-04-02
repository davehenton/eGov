package org.egov.edcr;

import java.math.BigDecimal;

import org.egov.edcr.entity.Building;
import org.egov.edcr.entity.ElectricLine;
import org.egov.edcr.entity.PlanDetail;
import org.egov.edcr.entity.PlanInformation;
import org.egov.edcr.entity.Plot;
import org.egov.edcr.entity.measurement.NonNotifiedRoad;
import org.egov.edcr.entity.measurement.NotifiedRoad;
import org.egov.edcr.entity.measurement.WasteDisposal;
import org.egov.edcr.entity.measurement.Yard;
import org.egov.edcr.rule.Rule31;

public class Rule26Test {

    public static void main(String[] args) {
        Rule31 rule23= new Rule31();
        PlanDetail planDetail= new PlanDetail();
        NotifiedRoad notifiedRoad= new NotifiedRoad();
        NonNotifiedRoad nonNotifiedRoad= new NonNotifiedRoad();
        notifiedRoad.setShortestDistanceToRoad(BigDecimal.valueOf(2));
        
        nonNotifiedRoad.setShortestDistanceToRoad(BigDecimal.valueOf(2));
     
        
        
        planDetail.setNonNotifiedRoads(java.util.Arrays.asList(nonNotifiedRoad));
        planDetail.setNotifiedRoads(java.util.Arrays.asList(notifiedRoad));
        Building buildingDetail= new Building ();
        buildingDetail.setFar(BigDecimal.valueOf(3.2));
        buildingDetail.setCoverage(BigDecimal.valueOf(65.3));

        Plot landDetail= new Plot();
        landDetail.setArea(BigDecimal.valueOf(33000));
        WasteDisposal wasteDisposal= new WasteDisposal();
        
         Yard frontYard = new Yard();
         frontYard.setPresentInDxf(true);
         frontYard.setMean(BigDecimal.valueOf(1.8));
         frontYard.setMinimumDistance(BigDecimal.valueOf(1.2));
         landDetail.setFrontYard(frontYard);
         
         Yard rearYard= new Yard();
 
         rearYard.setPresentInDxf(true);
         rearYard.setMean(BigDecimal.valueOf(1));
         rearYard.setMinimumDistance(BigDecimal.valueOf(0.5));
         landDetail.setRearYard(rearYard);
         
         Yard sideYard1= new Yard();
         sideYard1.setPresentInDxf(true);
         sideYard1.setMean(BigDecimal.valueOf(0.9));
         sideYard1.setMinimumDistance(BigDecimal.valueOf(0.8));
         landDetail.setSideYard1(sideYard1);

         Yard sideYard2= new Yard();
         sideYard2.setPresentInDxf(true);
         sideYard2.setMean(BigDecimal.valueOf(0.9));
         sideYard2.setMinimumDistance(BigDecimal.valueOf(0.9));
         landDetail.setSideYard2(sideYard2);

        PlanInformation planinformation= new PlanInformation();
        planinformation.setCrzZoneArea(true);
        planinformation.setSecurityZone(true);
        ElectricLine electricline= new ElectricLine();
        electricline.setPresentInDxf(true);
        
        planinformation.setOccupancy("Residential");
        
        electricline.setVoltage(BigDecimal.valueOf(33000));
        electricline.setHorizontalDistance(BigDecimal.valueOf(1.85));
        electricline.setVerticalDistance(BigDecimal.valueOf(4));

        planDetail.setElectricLine(electricline);
        
        planDetail.setPlanInformation(planinformation);
        //wasteDisposal.setPresentInDxf(true);
        planDetail.getUtility().getWasteDisposalUnits().add(wasteDisposal);
        buildingDetail.setMaxFloor(BigDecimal.valueOf(3));
        buildingDetail.setDistanceFromBuildingFootPrintToRoadEnd(BigDecimal.valueOf(12));
        buildingDetail.setBuildingHeight(BigDecimal.valueOf(10));
        planDetail.setBuilding(buildingDetail);
        planDetail.setPlot(landDetail);
        
        planDetail=rule23.validate(planDetail);
        planDetail=rule23.process(planDetail);
        System.out.println("Errors : " +  planDetail.getErrors() + "\n  Report output: "+ planDetail.reportOutput);
        
     /* for(RuleOutput ruleout: planDetail.reportOutput.ruleOutPuts)
      {
          System.out.println( ruleout);
         for (SubRuleOutput subruleout: ruleout.subRuleOutputs)
         { System.out.println( subruleout.);
       //  System.out.println( subruleout.result);
         }
      }*/
        
        
        
    }
}